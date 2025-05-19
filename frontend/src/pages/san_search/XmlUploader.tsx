import React, { useEffect, useRef, useState } from "react";
import XmlUploaderService from "../../data/services/xmlUploader/xmluploader-api-service";
import { Box, Button, Card, Container, Dialog, DialogActions, DialogContent, DialogTitle, Grid, MenuItem, Paper, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@mui/material';
import Header from "../../layouts/header/header";
import { xmlUploaderPayload } from "../../data/services/xmlUploader/xmluploader-payload";
import { useSelector } from "react-redux";

const XMLUploader: React.FC = () => {

    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [xmlUrl, setXmlUrl] = useState("");
    const [xmlText, setXmlText] = useState("");
    const [uploadType, setUploadType] = useState("file");
    const [selectedXmlContent, setSelectedXmlContent] = useState("");
    const [open, setOpen] = useState(false);
    const userDetails = useSelector((state: any) => state.loginReducer);
    const uid = userDetails.loginDetails.id;
    const [uploadStatus, setUploadStatus] = useState<string[]>(["", "", "", ""]);
    const [selectedFiles, setSelectedFiles] = useState<(File | null)[]>([null, null, null, null]);
    const [uploadTypes, setUploadTypes] = useState<string[]>(["file", "file", "file", "file"]);
    const fileInputRefs = useRef<(HTMLInputElement | null)[]>([]);

    const [xmlFiles, setxmlfiles] = useState<xmlUploaderPayload[]>([]);
    const xmlUploaderService = new XmlUploaderService();

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const [xmlfiles] = await Promise.all([
                xmlUploaderService.getXmlFiles(),
            ]);
            setxmlfiles(xmlfiles);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const handleFileClick = async (fileName: string) => {
        try {
            const response = await xmlUploaderService.getXmlfileName(fileName);
            setSelectedXmlContent(response);
            setOpen(true);
        } catch (error) {
            console.error("Error fetching file content:", error);
        }
    };

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>, index: number) => {
        if (event.target.files && event.target.files.length > 0) {
            const newFiles = [...selectedFiles];
            newFiles[index] = event.target.files[0];
            setSelectedFiles(newFiles);
            const newStatus = [...uploadStatus];
            newStatus[index] = "";
            setUploadStatus(newStatus);
        }
    };

    const handleUrlChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setXmlUrl(event.target.value);
    };

    const handleTextChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        setXmlText(event.target.value);
    };

    const handleUpload = async (index: number) => {
        if (!selectedFiles[index]) {
            updateUploadStatus(index, "Please select an XML file before uploading.");
            return;
        }
        updateUploadStatus(index, "");
        const formData = new FormData();
        formData.append("xmlFile", selectedFiles[index]!);
        formData.append("uid", uid);
        formData.append("pathId", (index + 1).toString());
        try {
            const response = await fetch("http://localhost:8096/api/v1/xmlUpload/file", {
                method: "POST",
                body: formData,
            });
            const data = await response.json();
            console.log(`Upload successful for XML ${index + 1}`, data);
            updateUploadStatus(index, "Upload successful!");
            const updatedFiles = [...selectedFiles];
            updatedFiles[index] = null;
            setSelectedFiles(updatedFiles);
            if (fileInputRefs.current[index]) {
                fileInputRefs.current[index]!.value = "";
            }
            fetchData();
        } catch (error) {
            console.error("Error:", error);
            updateUploadStatus(index, "Upload failed. Please try again.");
        }
    };

    const updateUploadStatus = (index: number, message: string) => {
        const newStatus = [...uploadStatus];
        newStatus[index] = message;
        setUploadStatus(newStatus);
    };

    return (
        <>
            <Box sx={{ display: 'flex' }}>
                <Header />
                <Box component="main" sx={{ flexGrow: 1, mt: 4 }}>
                    <Container maxWidth="lg" style={{ marginTop: '2%', maxWidth: '100%' }}>
                        <Typography variant="h5" fontWeight="bold" textAlign="center" gutterBottom>
                            XML File Uploader
                        </Typography>
                        <Grid container spacing={2} justifyContent="center">
                            {[1, 2, 3, 4].map((_, index) => (
                                // <Grid item xs={12} sm={6} md={3} key={index}>
                                <Grid item xs={12} sm={6} md={4} lg={3} xl={3} key={index}>
                                    <Card sx={{ width: '100%', padding: 3, boxShadow: 3, borderRadius: 2 }}>
                                        <Grid container spacing={2}>
                                            <Grid item xs={12}>
                                                <Select
                                                    fullWidth
                                                    value={uploadTypes[index]}
                                                    onChange={(e) => {
                                                        const newTypes = [...uploadTypes];
                                                        newTypes[index] = e.target.value;
                                                        setUploadTypes(newTypes);
                                                    }}
                                                    variant="outlined"
                                                    size="small"
                                                >
                                                    <MenuItem value="file">Single File Upload</MenuItem>
                                                </Select>
                                            </Grid>
                                            {uploadTypes[index] === "file" && (
                                                <Grid item xs={12}>
                                                    <input
                                                        ref={(el) => (fileInputRefs.current[index] = el)}
                                                        key={Date.now()}
                                                        type="file"
                                                        accept=".xml"
                                                        onChange={(e) => handleFileChange(e, index)}
                                                        style={{ display: "none" }}
                                                        id={`file-input-${index}`}
                                                    />
                                                    <label htmlFor={`file-input-${index}`}>
                                                        <Button variant="outlined" component="span">
                                                            Choose File
                                                        </Button>
                                                        <span style={{ marginLeft: "10px", fontSize: "12px", color: "#333" }}>
                                                            {selectedFiles[index]?.name || "No file chosen"}
                                                        </span>
                                                    </label>
                                                </Grid>
                                            )}
                                            <Grid item xs={12}>
                                                <Button
                                                    fullWidth
                                                    variant="contained"
                                                    color="primary"
                                                    onClick={() => handleUpload(index)}
                                                >
                                                    Upload XML {index + 1}
                                                </Button>
                                            </Grid>
                                            {uploadStatus[index] && (
                                                <Grid item xs={12}>
                                                    <Typography
                                                        color={
                                                            uploadStatus[index].includes("failed") ||
                                                                uploadStatus[index].includes("Please select an XML file")
                                                                ? "error"
                                                                : "success.main"
                                                        }
                                                        textAlign="center"
                                                    >
                                                        {uploadStatus[index]}
                                                    </Typography>
                                                </Grid>
                                            )}
                                        </Grid>
                                    </Card>
                                </Grid>
                            ))}
                        </Grid>
                    </Container>
                    <Card style={{ marginTop: '1%', padding: '1%', boxShadow: 'rgb(0 0 0 / 38%) 0px 4px 8px', width: '95%', margin: '2%' }}>
                        <TableContainer component={Paper} style={{ width: '100%', overflowX: 'auto' }} sx={{ maxHeight: { xs: "210px", md: "210px", lg: "210px", xl: "800px" } }}>
                            <Table size="small" stickyHeader aria-label="sticky table" style={{ margin: '0 auto' }}>
                                <TableHead sx={{ backgroundColor: '#cccdd1' }}>
                                    <TableRow className="tableHeading">
                                        <TableCell style={{ position: 'sticky', top: 0, backgroundColor: '#D3D3D3', fontWeight: 'bold' }}><strong>ID</strong></TableCell>
                                        <TableCell style={{ position: 'sticky', top: 0, backgroundColor: '#D3D3D3', fontWeight: 'bold' }}><strong>File Name</strong></TableCell>
                                        <TableCell style={{ position: 'sticky', top: 0, backgroundColor: '#D3D3D3', fontWeight: 'bold' }}><strong>Size (KB)</strong></TableCell>
                                        <TableCell style={{ position: 'sticky', top: 0, backgroundColor: '#D3D3D3', fontWeight: 'bold' }}><strong>Schema Version</strong></TableCell>
                                        <TableCell style={{ position: 'sticky', top: 0, backgroundColor: '#D3D3D3', fontWeight: 'bold' }}><strong>Record Count</strong></TableCell>
                                        <TableCell style={{ position: 'sticky', top: 0, backgroundColor: '#D3D3D3', fontWeight: 'bold' }}><strong>Status</strong></TableCell>
                                        <TableCell style={{ position: 'sticky', top: 0, backgroundColor: '#D3D3D3', fontWeight: 'bold' }}><strong>Created At</strong></TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {xmlFiles.length > 0 ? (
                                        xmlFiles.map((file) => (
                                            <TableRow key={file.id}>
                                                <TableCell>{file.id}</TableCell>
                                                <TableCell>
                                                    <Button onClick={() => handleFileClick(file.fileName)} color="primary">
                                                        {file.fileName}
                                                    </Button>
                                                </TableCell>
                                                <TableCell>{(file.fileSize / 1024).toFixed(2)}</TableCell>
                                                <TableCell>{file.xmlSchemaVersion}</TableCell>
                                                <TableCell>{file.recordCount}</TableCell>
                                                <TableCell>{file.status}</TableCell>
                                                <TableCell>{new Date(file.createdAt).toLocaleString()}</TableCell>
                                                {/* <TableCell>{file.createdAt}</TableCell> */}
                                            </TableRow>
                                        ))
                                    ) : (
                                        <TableRow>
                                            <TableCell colSpan={7} align="center">
                                                No XML files found.
                                            </TableCell>
                                        </TableRow>
                                    )}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Card>
                    <Dialog open={open} onClose={() => setOpen(false)} fullWidth maxWidth="md">
                        <DialogTitle>XML File Content</DialogTitle>
                        <DialogContent>
                            <pre style={{ whiteSpace: "pre-wrap", wordWrap: "break-word" }}>{selectedXmlContent}</pre>
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={() => setOpen(false)} color="primary">Close</Button>
                        </DialogActions>
                    </Dialog>
                </Box>
            </Box>
        </>
    );
};

export default XMLUploader;