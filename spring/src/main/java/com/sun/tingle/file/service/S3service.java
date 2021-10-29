package com.sun.tingle.file.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sun.tingle.file.db.entity.MissionFileEntity;
import com.sun.tingle.file.db.repo.MissionFileRepository;
import com.sun.tingle.file.responsedto.MissionFileRpDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class S3service {

    @Autowired
    MissionFileRepository missionFileRepository;


    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @ Value("${cloud.aws.credentials.profile-path}")
    private String bucketUrl;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }




    public MissionFileRpDto fileUpload(MultipartFile file,Long missionId, Long id) throws IOException {
        String fileName = file.getOriginalFilename(); // 중복 코드라서 뜨는 노란줄
        int len = fileName.lastIndexOf(".");
        String fileNameE = fileName.substring(len,fileName.length());
        String randomUuid= UUID.randomUUID().toString().replaceAll("-","");
        randomUuid += fileNameE;
        s3Client.putObject(new PutObjectRequest(bucket, randomUuid, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        String uuid = s3Client.getUrl(bucket,randomUuid).toString();
        MissionFileEntity mEntity = new MissionFileEntity();
            mEntity = mEntity.builder().fileUuid(uuid).fileName(fileName).missionId(missionId).id(id).
                            build();

            mEntity = missionFileRepository.save(mEntity);
            MissionFileRpDto missionFileRpDto = buildMissionFile(mEntity);
            return missionFileRpDto;

    }

    public String ProfileUpload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename(); // 중복 코드라서 뜨는 노란줄
        int len = fileName.lastIndexOf(".");
        String fileNameE = fileName.substring(len,fileName.length());
        String randomUuid= UUID.randomUUID().toString().replaceAll("-","");
        randomUuid += fileNameE;
        s3Client.putObject(new PutObjectRequest(bucket, randomUuid, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
       return s3Client.getUrl(bucket,randomUuid).toString();
    }




    public int deleteMissionFile(String uuid,Long id) {
        MissionFileEntity m = missionFileRepository.findByFileUuid(uuid);
        if(m == null) {
            return 0; // 삭제할 사진이 없다.
        }
        else if(m.getId() != id) {
            return 1; //권한 없다
        }
        else {
            missionFileRepository.delete(m);
            String s3Uuid = uuid.replace("https://d101.s3.ap-northeast-2.amazonaws.com/","");
            s3Client.deleteObject(new DeleteObjectRequest(bucket,s3Uuid));
            return 2; // 삭제 완료
        }
    }






    public void deleteProfileFile(String uuid) {
        String s3Uuid = uuid.replace("https://d101.s3.ap-northeast-2.amazonaws.com/","");
        s3Client.deleteObject(new DeleteObjectRequest(bucket,s3Uuid));
    }



//    public List<MissionFileRpDto> uploads(MultipartFile[] file,Long missionId,Long id) throws IOException {
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-mm-dd");
//        String dateName = date.format(new Date());
//        List<MissionFileRpDto> list = new ArrayList<>();
//        String[] url = new String[file.length];
//
//        for(int i=0; i<file.length; i++) {
//            String fileName = file[i].getOriginalFilename();
//            s3Client.putObject(new PutObjectRequest(bucket, fileName, file[i].getInputStream(), null)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//            url[i] = s3Client.getUrl(bucket,fileName).toString();
//
//            MissionFileEntity mEntity = new MissionFileEntity();
//            mEntity = mEntity.builder().fileUuid(url[i]).fileName(file[i].
//                            getOriginalFilename()).missionId(missionId).id(id).
//                            build();
//
//            mEntity = missionFileRepository.save(mEntity);
//            MissionFileRpDto missionFileRpDto = buildMissionFile(mEntity);
//            list.add(missionFileRpDto);
//        }
//        return list;
//    }





    public  MissionFileRpDto buildMissionFile(MissionFileEntity mEntity) {
        MissionFileRpDto mDto = new MissionFileRpDto();

        mDto = mDto.builder().fileName(mEntity.getFileName())
                        .fileUuid(mEntity.getFileUuid()).
                build();

        return mDto;
    }

    public List<MissionFileRpDto> selectFileList(Long missionId) {
        List<MissionFileEntity> list = new ArrayList<>();
        List<MissionFileRpDto> list2 = new ArrayList<>();
        list = missionFileRepository.findByMissionId(missionId);

        for(int i=0; i<list.size(); i++) {
            MissionFileRpDto mDto = buildMissionFile(list.get(i));
            list2.add(mDto);
        }

        return list2;

    }



//    public InputStream download(String uuid) throws IOException {
//
////        MultipartFile[] file = new MultipartFile[]
//
//        S3Object o = s3Client.getObject(new GetObjectRequest(bucket,uuid));
//        return o.getObjectContent();
////        S3ObjectInputStream objectInputStream = o.getObjectContent();
//
////        byte[] bytes = IOUtils.toByteArray(objectInputStream);
////        Resource resource = (Resource) new ByteArrayResource(bytes);
////        return resource;
//
//    }


//    public void deleteMissionFile(Long fileId,String fileName) {
//        s3Client.deleteObject(new DeleteObjectRequest(bucket,fileName));
//        missionFileRepository.deleteById(fileId);
//    }
}