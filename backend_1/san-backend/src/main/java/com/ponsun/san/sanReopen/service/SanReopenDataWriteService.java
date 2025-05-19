package com.ponsun.san.sanReopen.service;

import com.ponsun.san.sanReopen.data.SanReopenData;

import java.util.List;

public interface SanReopenDataWriteService {
     List<SanReopenData> fetchAllLReopen( List<Integer> uid,String startDate, String endDate) ;


}
