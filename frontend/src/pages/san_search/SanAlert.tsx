import React, { useState, useEffect } from 'react';
import Header from '../../layouts/header/header';
import { Box, Grid, Card, CardContent, TextField, MenuItem, Button, Chip } from '@mui/material';
import SanAlertApiService from '../../data/services/san_search/sanAlert/sanAlert-api-service';
import { Status, Users } from '../../data/services/san_search/sanAlert/sanAlert-payload';

const SanAlert = () => {

    const [fromDate, setFromDate] = useState('');
    const [toDate, setToDate] = useState('');
    const [selectedStatus, setSelectedStatus] = useState<string[]>([]);
    const [status, setStatus] = useState<Status[]>([]);
    const [selectedUsers, setSelectedUsers] = useState<string[]>([]);
    const [user, setUser] = useState<Users[]>([]);

    const sanAlertServices = new SanAlertApiService();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [users, statuses] = await Promise.all([
                    sanAlertServices.getUsers(),
                    sanAlertServices.getStatus()
                ]);
                setUser(users);
                setStatus(statuses);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData();
    }, []);

    const handleRemoveUser = (valueToRemove: string) => {
        setSelectedUsers((prevSelected) => prevSelected.filter(value => value !== valueToRemove));
    };

    const handleRemoveStatus = (valueToRemove: string) => {
        setSelectedStatus((prevSelected) => prevSelected.filter(value => value !== valueToRemove));
    };

    const handleSelectUser = (e: any) => {
        const selectedValues = e.target.value;
        setSelectedUsers(selectedValues);
    };

    const handleSelectStatus = (e: any) => {
        const selectedValues = e.target.value;
        setSelectedStatus(selectedValues);
    };

    const handleReset = () => {
        setFromDate('');
        setToDate('');
        setSelectedUsers([]);
        setSelectedStatus([]);
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <Header />
            <Box component="main" sx={{ flexGrow: 1, mt: 4, p: 2 }}>
                <Card sx={{ maxWidth: '100%', margin: 'auto', padding: '1%', boxShadow: 'rgb(0 0 0 / 28%) 0px 4px 8px' }}>
                    <h6>ALERT</h6>
                    <CardContent>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6} md={2.5}>
                                <TextField
                                    fullWidth
                                    select
                                    size="small"
                                    label="User"
                                    value={selectedUsers}
                                    onChange={handleSelectUser}
                                    SelectProps={{
                                        multiple: true,
                                    }}
                                >
                                    {user.map((option) => (
                                        <MenuItem key={option.id} value={option.id.toString()}>
                                            {option.userName}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                    {selectedUsers.map((userId) => {
                                        const userName = user.find(u => u.id === Number(userId))?.userName;
                                        return userName ? (
                                            <Chip
                                                key={userId}
                                                label={userName}
                                                onDelete={() => handleRemoveUser(userId)}
                                                size="small"
                                            />
                                        ) : null;
                                    })}
                                </Box>
                            </Grid>
                            <Grid item xs={12} sm={6} md={2.5}>
                                <TextField
                                    fullWidth
                                    select
                                    size="small"
                                    label="Status"
                                    value={selectedStatus}
                                    onChange={handleSelectStatus}
                                    SelectProps={{
                                        multiple: true,
                                    }}
                                >
                                    {status.map((option) => (
                                        <MenuItem key={option.id} value={option.id.toString()}>
                                            {option.name}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5, mt: 1 }}>
                                    {selectedStatus.map((statusId) => {
                                        const statusName = status.find(s => s.id === Number(statusId))?.name;
                                        return statusName ? (
                                            <Chip
                                                key={statusId}
                                                label={statusName}
                                                onDelete={() => handleRemoveStatus(statusId)}
                                                size="small"
                                            />
                                        ) : null;
                                    })}
                                </Box>
                            </Grid>
                            <Grid item xs={12} sm={6} md={2.5}>
                                <TextField
                                    fullWidth
                                    type="date"
                                    size="small"
                                    label="From Date"
                                    InputLabelProps={{ shrink: true }}
                                    value={fromDate}
                                    onChange={(e) => setFromDate(e.target.value)}
                                    sx={{
                                        '& .MuiInputBase-root': { height: 32 }
                                    }}
                                    inputProps={{
                                        style: { height: 16, padding: '8px' }
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={2.5}>
                                <TextField
                                    fullWidth
                                    type="date"
                                    size="small"
                                    label="To Date"
                                    InputLabelProps={{ shrink: true }}
                                    value={toDate}
                                    onChange={(e) => setToDate(e.target.value)}
                                    sx={{
                                        '& .MuiInputBase-root': { height: 32 }
                                    }}
                                    inputProps={{
                                        style: { height: 16, padding: '8px' }
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={2} mt={1}>
                                <Button variant="contained" color="primary" size="small">Search</Button>
                                <Button variant="contained" color="primary" size="small" style={{ marginLeft: '2%' }} onClick={handleReset}>Reset</Button>
                            </Grid>
                        </Grid>
                    </CardContent>
                </Card>
            </Box>
        </Box>
    );
}

export default SanAlert;