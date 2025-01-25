import NavigationBar from "@/components/NavigationBar/NavigationBar.jsx"
import HomePage from "@/components/HomePage/HomePage.jsx"
import LoginPage from "@/components/LoginPage/LoginPage.jsx";
import RegisterPage from "@/components/RegisterPage/RegisterPage.jsx";
import ShoppingCartPage from "@/components/ShoppingCartPage/ShoppingCartPage.jsx";
import AccountPage from "@/components/AccountPage/AccountPage";
import {Routes, Route, useLocation} from "react-router";
import { useEffect, useState } from "react";

function App() {
  const location = useLocation();
  const [searchKeyword, setSearchKeyword] = useState("");

  useEffect(() => {
  }, [location]);

  return (
    <>
      <NavigationBar
        keyword={searchKeyword}
        setSearchKeyword={setSearchKeyword}
      />
      <Routes>
        <Route path="/" element={<HomePage/>}/>
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/register" element={<RegisterPage/>}/>
        <Route path="/store/cart" element={<ShoppingCartPage/>}/>
        <Route path="/account" element={<AccountPage/>}/>
      </Routes>
    </>
  );
}

export default App
