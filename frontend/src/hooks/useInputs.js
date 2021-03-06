import { useCallback, useReducer } from "react"

function reducer(state, action) {
  switch (action.type) {
    case "CHANGE_INPUTS": {
      return {
        ...state,
        [action.name]: {
          ...state[action.name],
          value: action.value,
        },
      }
    }
    case "CHANGE_ERRORS": {
      return {
        ...state,
        [action.name]: {
          ...state[action.name],
          errors: action.errors,
        },
      }
    }
    default: {
      return state
    }
  }
}

const useInputs = (initialForm) => {
  const [state, dispatch] = useReducer(reducer, initialForm)

  const validate = useCallback(
    (name, value) => {
      const errors = {}
      const { validators } = state[name]
      validators.forEach((v) => {
        console.log(v(value))
        const res = v(value)
        if (!res.valid) {
          errors[res.type] = res.message
        }
      })
      dispatch({
        type: "CHANGE_ERRORS",
        name,
        errors,
      })
    },
    [state]
  )

  const handleChange = useCallback(
    (e) => {
      const { name, value } = e.target
      validate(name, value)
      dispatch({
        type: "CHANGE_INPUTS",
        name,
        value,
      })
    },
    [validate]
  )

  return [state, handleChange]
}

export default useInputs
