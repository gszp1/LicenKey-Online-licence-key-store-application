import { Routes, Route } from "react-router";
import HomePage from "@/components/HomePage/HomePage";
import NavigationBar from "@/components/NavigationBar/NavigationBar.module.css"

function App() {
  return (
    <>
      <NavigationBar
      />
      <Routes>
        <Route path='/' element={<HomePage/>}></Route>
        
      </Routes>
    </>
  )
}

export default App
