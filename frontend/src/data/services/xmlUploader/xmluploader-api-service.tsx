import HttpClientWrapper from "../../api/http-client-wrapper";
import { xmlUploaderPayload } from "./xmluploader-payload";

class XmlUploaderService {

    private httpClientWrapper: HttpClientWrapper;

    constructor() {
        this.httpClientWrapper = new HttpClientWrapper();
    }

    saveXmlFiles = async (formData: FormData) => {
        try {
            const formData = new FormData();
            const response = await this.httpClientWrapper.xmlpost('/api/v1/xmlUpload/file', formData, {
                headers: { "Content-Type": undefined }
            });
            return response;
        } catch (error) {
            throw error;
        }
    };

    getXmlFiles = async () => {
        try {
            const response = await this.httpClientWrapper.get('/api/v1/xmlUpload/fetchAllUploadxml');
            return response;
        } catch (error) {
            throw error;
        }
    };

    getXmlfileName = async (fileName: string) => {
        try {
            const response = await this.httpClientWrapper.get(`/api/XMLUpload/file-content?fileName=${fileName}`);
            return response;
        } catch (error) {
            throw error;
        }
    };

}

export default XmlUploaderService;