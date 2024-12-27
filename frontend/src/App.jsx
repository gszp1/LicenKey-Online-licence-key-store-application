import NavigationBar from "@/components/NavigationBar/NavigationBar.jsx"
import HomePage from "@/components/HomePage/HomePage.jsx"
import LoginPage from "@/components/LoginPage/LoginPage.jsx";
import RegisterPage from "@/components/RegisterPage/RegisterPage.jsx";

import {Routes, Route} from "react-router";

function App() {
  return (
    <>
      <NavigationBar/>
      <Routes>
        <Route path="/" element={<HomePage/>}/>
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/register" element={<RegisterPage/>}/>
      </Routes>
    </>
  )
}

export default App
