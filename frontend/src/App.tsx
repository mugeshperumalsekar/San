
import './styles/global.css';
import AppRouter from './router/app-route';
import "bootstrap/dist/css/bootstrap.min.css";
import 'react-toastify/dist/ReactToastify.css';
import { GlobalStyles } from './styles';
import XMLUploader from './pages/san_search/XmlUploader';


function App() {
  return (
    <>
      <GlobalStyles />
      <AppRouter />
      {/* <XMLUploader /> */}
    </>
  );
}

export default App;
