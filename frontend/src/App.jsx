import NavigationBar from "@/components/NavigationBar/NavigationBar.jsx"
import HomePage from "@/components/HomePage/HomePage.jsx"
import LoginPage from "@/components/LoginPage/LoginPage";
import {Routes, Route} from "react-router";

function App() {
  return (
    <>
      <NavigationBar/>
      <Routes>
        <Route path="/" element={<HomePage/>}/>
        <Route path="/login" element={<LoginPage/>}/>
      </Routes>
    </>
  )
}

export default App
