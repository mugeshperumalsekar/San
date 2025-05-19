import HttpClientWrapper from "../../../api/http-client-wrapper";

class RIFApiService {

    private httpClientWrapper: HttpClientWrapper;

    constructor() {
        this.httpClientWrapper = new HttpClientWrapper();
    }

    getRIF = async () => {
        try {
            const levelId = 3;
            const levelIds = 4;
            const response = await this.httpClientWrapper.get(`/api/v1/levelFour?levelId=${levelId}&levelIds=${levelIds}`);
            return response;
        } catch (error) {
            console.error("Error:", error);
            throw error;
        }
    };

    getpendingRIF = async () => {
        try {
            const levelId = 0;
            const response = await this.httpClientWrapper.get(`/api/v1/levelFour?levelId=${levelId}`);
            return response;
        } catch (error) {
            console.error("Error:", error);
            throw error;
        }
    };

}

export default RIFApiService;