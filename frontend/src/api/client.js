import axios from "axios"

const client = axios.create({ baseURL: "/api" })

client.interceptors.request.use((config) => {
  // 1. accessToken이 있으면 헤더에 accessToken을 삽입한 뒤 요청을 보낸다.
  // 2. accessToken이 없으면 그대로 요청을 보낸다.
  const localStorageUser = JSON.parse(localStorage.getItem("currentUser"))
  const accessToken = localStorageUser?.token?.accessToken
  // 토큰이 있으면, 헤더에 토큰을 삽입하여 요청한다.
  if (accessToken && !config.url.includes("reissue")) {
    config.headers.Authorization = `Bearer ${accessToken}`
  }

  return config
})

client.interceptors.response.use(
  (res) => res,
  async (error) => {
    // 1. accessToken 관련 에러가 발생한 경우
    // 1-1. refreshToken이 없으면, localStorage를 비운다.
    // 1-2. refreshToken이 있으면, reissue 요청을 보낸다.

    // 2. reissue 요청
    // 2-1. reissue 요청이 실패하면, localStorage를 비운다.
    // 2-2. reissue 요청이 성공하면, 새로 받은 accessToken을 localStorage에 저장한다.
    console.dir(error)
    const {
      config,
      response: { status },
    } = error
    const localStorageUser = JSON.parse(localStorage.getItem("currentUser"))
    switch (status) {
      // (1)
      case 403: {
        // (1-2)
        if (localStorageUser) {
          const originalRequest = config
          const refreshToken = localStorageUser?.token?.refreshToken
          try {
            const { data } = await client.get(`auth/reissue`, {
              headers: {
                refreshToken,
              },
            })
            // (2-2)
            const { accessToken } = data.data
            localStorage.setItem({
              ...localStorageUser,
              token: { accessToken, refreshToken },
            })
            client.defaults.headers.common.Authorization = `Bearer ${accessToken}`
            originalRequest.headers.Authorization = `Bearer ${accessToken}`
            return axios(originalRequest)
          } catch (_error) {
            // (2-1)
            // LocalStorage에 있는 데이터 삭제
            localStorage.removeItem("currentUser")

            // 로그인 페이지로 보내려면?
            return Promise.reject(_error)
          }
        }
      }
    }
    return Promise.reject(error)
  }
)

export function applyToken(token) {
  client.defaults.headers.common["Authorization"] = `Bearer ${token}`
}

export function resetToken(token) {
  delete client.defaults.headers.common["Authorization"]
}

export default client
