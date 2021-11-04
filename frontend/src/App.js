// import { useEffect } from "react"
import { Switch } from "react-router"
import "./App.css"
import AppRoute from "./components/AppRoute"
import NoMatchRoute from "./components/NoMatchRoute"
// import { useAuthDispatch, useAuthState } from "./context"
// import { silentRefresh } from "./context/action"
import routes from "./routes"

function App() {
  // const dispatch = useAuthDispatch()
  // const auth = useAuthState()

  // useEffect(() => {
  //   console.log(auth)
  //   silentRefresh(dispatch, auth.token)
  // }, [])
  return (
    <main className="h-full max-h-full pb-16">
      <Switch>
        {routes.map(({ path, component, ...rest }) => (
          <AppRoute key={path} component={component} path={path} {...rest} />
        ))}
        <NoMatchRoute />
      </Switch>
    </main>
  )
}

export default App
