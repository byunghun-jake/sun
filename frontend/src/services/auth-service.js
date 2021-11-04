import client from "../api/client"

const register = async (data) => {
  const res = await client.post("auth", data)
  return res.data
}

const login = async (data) => {
  try {
    const res = await client.post("auth/login", data)
    if (res.data.accessToken) {
      localStorage.setItem(
        "user",
        JSON.stringify({
          ...res.data?.member,
          accessToken: res.data.accessToken,
          refreshToken: res.data.refreshToken,
        })
      )
    }
    return res.data
  } catch (error) {
    console.log(error.response)
  }
}

const logout = async () => {
  await client.delete("members/logout")
  localStorage.removeItem("user")
}

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"))
}

const authService = {
  register,
  login,
  logout,
  getCurrentUser,
}

export default authService
