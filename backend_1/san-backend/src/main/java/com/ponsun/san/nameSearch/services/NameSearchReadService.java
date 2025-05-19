package com.ponsun.san.nameSearch.services;




import com.ponsun.san.nameSearch.data.NameSearchData;

import java.util.List;

public interface NameSearchReadService {

    List<NameSearchData> fetchAllNameSearch(Integer kycId);
}
