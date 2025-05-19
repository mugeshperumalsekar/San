export interface xmlUploaderPayload {
    id: number;
    fileName: string;
    fileSize: number;
    xmlSchemaVersion: string;
    recordCount: number;
    ipAddress: string;
    pathId: number;
    status: string;
    valid: boolean;
    errorMessage: string;
    processingTime: number;
    createdAt: string;
}