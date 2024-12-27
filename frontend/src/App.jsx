import NavigationBar from "@/components/NavigationBar/NavigationBar.jsx"
import HomePage from "@/components/HomePage/HomePage.jsx"
import LoginPage from "@/components/LoginPage/LoginPage.jsx";
import RegisterPage from "@/components/RegisterPage/RegisterPage.jsx";
import ShoppingCartPage from "@/components/ShoppingCartPage/ShoppingCartPage.jsx";

import {Routes, Route} from "react-router";

function App() {
  return (
    <>
      <NavigationBar/>
      <Routes>
        <Route path="/" element={<HomePage/>}/>
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/register" element={<RegisterPage/>}/>
        <Route path="/store/cart" element={<ShoppingCartPage/>}/>
      </Routes>
    </>
  )
}

export default App
