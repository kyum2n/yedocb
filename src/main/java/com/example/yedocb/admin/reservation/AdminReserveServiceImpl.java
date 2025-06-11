package com.example.yedocb.admin.reservation;

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.example.yedocb.reservation.entity.Reservation;
import com.example.yedocb.reservation.repository.ReservationMapper;
import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReserveServiceImpl implements AdminReserveService {

    private final ReservationMapper reservationMapper;
    private final UserMapper userMapper;
    private final JavaMailSender mailSender;

    @Override
    public void sendReminderEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Reservation> reservations = reservationMapper.selectByDate(java.sql.Date.valueOf(tomorrow));

        for (Reservation r : reservations) {
            User user = userMapper.selectByUId(r.getUId());
            if (user != null) {
                try {
                    sendEmail(user.getUEmail(), r);
                } catch (MessagingException e) {
                    System.err.println("메일 전송 실패: " + user.getUEmail());
                }
            }
        }
    }

    private void sendEmail(String to, Reservation reservation) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("YeDoc 예약 알림");
        helper.setText("안녕하세요, 내일 예약이 예정되어 있습니다.\n" +
                "시술명: " + reservation.getTName() + "\n" +
                "날짜: " + reservation.getConsultDate() + "\n" +
                "시간: " + reservation.getConsultTime(), false);

        mailSender.send(message);
    }

    // 모든 예약 목록 조회
    @Override
    public List<Reservation> getAllReservations() {
        return reservationMapper.selectAllReservation();
    }

    // 예약 상태 변경
    @Override
    public void updateReservationStatus(int rId, String status) {
        reservationMapper.updateStatus(rId, status);
    }

    // 예약 등록 및 변경사항 적용
    @Override
    public void modifyReservationSchedule(Reservation reservation) {
    	if(reservation.getRId() == 0) {
    		reservationMapper.insertReservation(reservation);
    	} else {
    		reservationMapper.updateReservation(reservation);
    	}
    }

    // 예약 삭제
    @Override
    public void deleteReservation(int rId) {
        reservationMapper.deleteReservation(rId);
    }
}
