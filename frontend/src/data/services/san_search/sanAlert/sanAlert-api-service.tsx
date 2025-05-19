import HttpClientWrapper from "../../../api/http-client-wrapper";

class SanAlertApiService {

    private httpClientWrapper: HttpClientWrapper;

    constructor() {
        this.httpClientWrapper = new HttpClientWrapper();
    }

    getUsers = async () => {
        try {
            const response = await this.httpClientWrapper.get('/api/v1/users');
            return response;
        } catch (error) {
            console.error('Error fetching the getUsers:', error);
            throw error;
        }
    };

    getStatus = async () => {
        try {
            const response = await this.httpClientWrapper.get('/api/v1/Status');
            return response;
        } catch (error) {
            console.error('Error fetching the getStatus:', error);
            throw error;
        }
    };

}

export default SanAlertApiService;