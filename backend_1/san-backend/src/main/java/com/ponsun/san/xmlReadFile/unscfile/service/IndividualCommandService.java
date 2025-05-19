package com.ponsun.san.xmlReadFile.unscfile.service;

import com.ponsun.san.infrastructure.utils.Response;

import java.io.IOException;
import java.sql.SQLException;

public interface IndividualCommandService {

    Response saveCsvData(String filePath, String fileName) throws SQLException, IOException;
}
