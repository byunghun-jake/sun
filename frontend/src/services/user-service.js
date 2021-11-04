import client from "../api/client"
import authHeader from "./auth-header"

const getMember = (id) => {
  return client.get(`/members/${id}`, { headers: authHeader() })
}

const updateMember = (formData) => {
  return client.put(`/members`, formData, { headers: authHeader() })
}

const deleteMember = (id) => {
  return client.delete(`/members/${id}`, { headers: authHeader() })
}

const updateProfileImage = (formData) => {
  return client.put(`/members/profile-image`, formData, {
    headers: authHeader(),
  })
}

const changePassword = (formData) => {
  return client.put(`/members/change-password`, formData, {
    headers: authHeader(),
  })
}
