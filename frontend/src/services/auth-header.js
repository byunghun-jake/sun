export default function authHeader() {
  const user = JSON.parse(localStorage("user"))

  if (user?.accessToken) {
    return { Authorication: `Bearer ${user.accessToken}` }
  } else {
    return {}
  }
}
