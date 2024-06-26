package com.kosa.pos.swing.review;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.kosa.pos.dao.MenuDAOImpl;
import com.kosa.pos.dto.MenuDetail;
import com.kosa.pos.swing.common.OrderState;
import com.kosa.pos.swing.main.CardLayoutManager;
import com.kosa.pos.swing.main.ContentPaneManager;
import com.kosa.pos.swing.main.Index;

public class ReviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MenuDAOImpl menuDao = new MenuDAOImpl();

	public ReviewPanel(Index index) {
		// ReviewPanel 크기를 index 패널과 동일하게 설정
		setPreferredSize(new Dimension(950, 650));
		setLayout(null);

		// 상단 패널 구성
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 28, 950, 130);
		add(topPanel);
		topPanel.setLayout(null);

		JPanel upperPanel = new JPanel();
		upperPanel.setBounds(0, 0, 950, 65);
		topPanel.add(upperPanel);
		upperPanel.setLayout(null);

		JLabel reviewPromptLabel = new JLabel("리뷰를 작성해주세요!");
		reviewPromptLabel.setFont(new Font("굴림", Font.PLAIN, 25));
		reviewPromptLabel.setBounds(353, 17, 244, 30);
		upperPanel.add(reviewPromptLabel);

		JPanel lowerPanel = new JPanel();
		lowerPanel.setBounds(0, 65, 950, 65);
		topPanel.add(lowerPanel);
		lowerPanel.setLayout(null);

		JLabel orderMenuLabel = new JLabel("주문한 메뉴");
		orderMenuLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		orderMenuLabel.setBounds(61, 18, 112, 28);
		lowerPanel.add(orderMenuLabel);

		JButton quitBtn = new JButton("나가기");
		quitBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index.setBounds(500,500,1250,700);
				index.setLocationRelativeTo(null);
				CardLayoutManager.getCardLayout().show(ContentPaneManager.getContentPane(), "menu");
			}
		});
		quitBtn.setBounds(785, 18, 106, 28);
		lowerPanel.add(quitBtn);

		// ScrollPane 설정
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Y_AXIS로 설정하여 수직 배치

		// orderId 가져오기
		int orderId = OrderState.getOrderId();

		List<Integer> menuIds = menuDao.fetchMenuIdByOrderId(orderId);
		System.out.println("menuIds: " + menuIds);

		// menuId List를 이용해 ReviewMenuPanel 생성 및 contentPanel에 추가
		for (Integer menuId : menuIds) {
			Optional<MenuDetail> menuDetail = menuDao.findById(menuId);

			ReviewMenuPanel reviewMenuPanel = new ReviewMenuPanel(menuId, menuDetail.get().getMenu().getMenu_path(),
					menuDetail.get().getMenu().getName(), menuDetail.get().getCount(), menuDetail.get().getAvgScore());
			contentPanel.add(reviewMenuPanel);
			System.out.println("Menu Detail: " + menuDetail.get());

		}

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBounds(0, 180, 938, 470);
		add(scrollPane);
	}
}
