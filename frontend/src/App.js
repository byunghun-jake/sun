import { Route, Switch } from "react-router"
import "./App.css"
import ResetPassword from "./pages/ResetPassword"
import FindAuth from "./pages/FindAuth"
import Login from "./pages/Login"
import Profile from "./pages/Profile"
import Register from "./pages/Register"
import EditProfile from "./pages/EditProfile"
import ChangePassword from "./pages/ChangePassword"
import Calendars from "./pages/Calendars"

function App() {
  return (
    <Switch>
      <Route path="/login">
        <Login />
      </Route>
      <Route path="/register">
        <Register />
      </Route>
      <Route path="/auth/find-auth">
        <FindAuth />
      </Route>
      <Route path="/auth/reset-password">
        <ResetPassword />
      </Route>
      <Route path="/calendars/:calendarId">
        <Calendars />
      </Route>
      <Route path="/profile/edit" exact>
        <EditProfile />
      </Route>
      <Route path="/profile/change-password" exact>
        <ChangePassword />
      </Route>
      <Route path="/profile/:email">
        <Profile />
      </Route>
    </Switch>
  )
}

export default App
