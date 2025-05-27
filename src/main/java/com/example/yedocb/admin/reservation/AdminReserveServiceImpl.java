package com.example.yedocb.admin.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.yedocb.reservation.entity.Reservation;

/**
 * packageName    : com/example/yedocb/admin/reservation
 * fileName       : AdminReserveServiceImpl.java 
 * author         : JKW
 * date           : 2025/05/27
 * description    :
 * ===========================================================
 * DATE          AUTHOR                 NOTE
 * -----------------------------------------------------------
 * 2025/05/27     JKW                  최초작성          
 */
@Service
public class AdminReserveServiceImpl implements AdminReserveService {

	@Override
	public List<Reservation> getAllReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReservationStatus(int rId, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyReservationSchedule(Reservation reservation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteReservation(int rId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendReminderEmails() {
		// TODO Auto-generated method stub

	}

}
