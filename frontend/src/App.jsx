import NavigationBar from "@/components/NavigationBar/NavigationBar.jsx"
import HomePage from "@/components/HomePage/HomePage.jsx"
import {Routes, Route} from "react-router";

function App() {
  return (
    <>
      <NavigationBar/>
      <Routes>
        <Route path="/" element={<HomePage/>}/>
      </Routes>
    </>
  )
}

export default App
