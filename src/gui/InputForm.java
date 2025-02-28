package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.Controller;
import structures.StructInputForm;

public class InputForm extends JFrame
		implements ActionListener {

	private JPanel ghsPanel;

	private JComboBox<String> comboTantou;
	private JTextField textHinban;
	private JTextField textDisplay;
	private JTextField textSG;
	private JTextField textAppe;
	private CheckGhsDb2 cgd2;
	private JFrame inputFrame;
	private ButtonGroup paintTypeGroup;
	private ButtonGroup singleMixtureGroup;
	private JTextField textGhsBunrui;
	private JTextField textGhsNo;
	private JTextField textGhsYouki;
	private JTextField textGhsName;
	private JCheckBox checkNite;
	private JTextField textRenewalDeadline;
	private JCheckBox checkChn;

	public InputForm() {

		//CheckGhsDb2をｲﾝｽﾀﾝｽ化しておく
		cgd2 = new CheckGhsDb2();
		inputFrame = this;

		inputFrame.setTitle("SDS自動作成インプット");
		this.setLayout(null);
		//this.setBounds(100, 100, 700, 850);
		this.setSize(700, 750);
		this.setLocationRelativeTo(null); //JFrameをwindowの中心に表示
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//タイトルパネルの作成
		JPanel tytlePanel = new JPanel();
		tytlePanel.setLayout(null);

		JLabel labelTytle = new JLabel("SDS自動作成アプリ");
		labelTytle.setFont(new Font("MS ゴシック", Font.BOLD, 30));
		labelTytle.setBounds(200, 0, 300, 60);
		labelTytle.setForeground(Color.WHITE);

		tytlePanel.add(labelTytle);
		tytlePanel.setOpaque(true);
		tytlePanel.setBackground(new Color(0, 85, 255));

		//インプットパネルの作成
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(null);

		JLabel labelTantou = new JLabel("担当者");
		labelTantou.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelTantou.setBounds(10, 10, 80, 30);
		JLabel labelHinban = new JLabel("品番");
		labelHinban.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelHinban.setBounds(10, 50, 80, 30);
		JLabel labelCaution = new JLabel("<-- 変更後は必ず[Enter]");
		labelCaution.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelCaution.setBounds(300, 50, 200, 30);
		JLabel labelDisplay = new JLabel("SDS表示名");
		labelDisplay.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelDisplay.setBounds(10, 90, 80, 30);
		JLabel labelSG = new JLabel("比重");
		labelSG.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelSG.setBounds(10, 130, 80, 30);
		JLabel labelAppe = new JLabel("塗料外観");
		labelAppe.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelAppe.setBounds(10, 170, 80, 30);

		String[] tantous = {
				"和泉　宏之", "鈴木　重義", "髙橋　信一",
				"丸山　岳", "荒添　圭朗", "嘉規　創真",
				"吉田　純貴", "徳武　宗仁", "尾頭　章公"
		};

		comboTantou = new JComboBox<String>(tantous);
		comboTantou.setBounds(100, 10, 150, 30);
		textHinban = new JTextField();
		textHinban.setBounds(100, 50, 200, 30);
		textDisplay = new JTextField();
		textDisplay.setBounds(100, 90, 200, 30);
		textSG = new JTextField();
		textSG.setBounds(100, 130, 200, 30);
		textAppe = new JTextField();
		textAppe.setBounds(100, 170, 200, 30);

		inputPanel.add(labelTantou);
		inputPanel.add(labelHinban);
		inputPanel.add(labelCaution);
		inputPanel.add(labelDisplay);
		inputPanel.add(labelSG);
		inputPanel.add(labelAppe);
		inputPanel.add(comboTantou);
		inputPanel.add(textHinban);
		inputPanel.add(textDisplay);
		inputPanel.add(textSG);
		inputPanel.add(textAppe);

		//塗料タイプパネルの作成
		JPanel paintTypePanel = new JPanel();
		paintTypePanel.setLayout(null);
		LineBorder border = new LineBorder(Color.BLACK, 1, true);
		paintTypePanel.setBorder(border);

		//ｸﾞﾙｰﾌﾟを作る
		paintTypeGroup = new ButtonGroup();

		JRadioButton ippan = new JRadioButton("一般塗料");
		ippan.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		paintTypeGroup.add(ippan);
		ippan.setBounds(10, 10, 100, 30);
		JRadioButton yusei = new JRadioButton("油性塗料");
		yusei.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		paintTypeGroup.add(yusei);
		yusei.setBounds(10, 50, 100, 30);
		JRadioButton metal = new JRadioButton("ﾒﾀﾘｯｸ塗料");
		metal.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		paintTypeGroup.add(metal);
		metal.setBounds(10, 90, 100, 30);
		JRadioButton bpo = new JRadioButton("BPO入り塗料");
		bpo.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		paintTypeGroup.add(bpo);
		bpo.setBounds(10, 130, 100, 30);

		paintTypePanel.add(ippan);
		paintTypePanel.add(yusei);
		paintTypePanel.add(metal);
		paintTypePanel.add(bpo);

		//単体、混合物パネルの作成
		JPanel singleMixturePanel = new JPanel();
		singleMixturePanel.setLayout(null);
		//LineBorder border2 = new LineBorder(Color.BLACK,1,true);
		singleMixturePanel.setBorder(border);

		//ｸﾞﾙｰﾌﾟを作る
		singleMixtureGroup = new ButtonGroup();

		JRadioButton mixture = new JRadioButton("混合物");
		mixture.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		singleMixtureGroup.add(mixture);
		mixture.setBounds(10, 10, 100, 30);
		JRadioButton single = new JRadioButton("単体成分");
		single.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		singleMixtureGroup.add(single);
		single.setBounds(10, 80, 100, 30);

		JLabel labelMixture = new JLabel("塗料,混合ｼﾝﾅｰ など");
		labelMixture.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelMixture.setBounds(10, 30, 200, 30);

		JLabel labelSingle = new JLabel("ｱﾙﾐﾍﾟｰｽﾄ,顔料単体,ｼﾝﾅｰ単体 など");
		labelSingle.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelSingle.setBounds(10, 100, 200, 30);

		singleMixturePanel.add(mixture);
		singleMixturePanel.add(single);
		singleMixturePanel.add(labelMixture);
		singleMixturePanel.add(labelSingle);

		//GHSパネルの作成
		ghsPanel = new JPanel();
		ghsPanel.setLayout(null);
		LineBorder border3 = new LineBorder(Color.BLACK, 1, true);
		ghsPanel.setBorder(border3);

		JLabel labelGhsBunrui = new JLabel("国連分類");
		labelGhsBunrui.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelGhsBunrui.setBounds(10, 10, 80, 30);
		JLabel labelGhsBunruiRei = new JLabel("例) ｸﾗｽ3, ｸﾗｽ4.1 など");
		labelGhsBunruiRei.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelGhsBunruiRei.setBounds(240, 10, 150, 30);
		JLabel labelGhsNo = new JLabel("国連番号");
		labelGhsNo.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelGhsNo.setBounds(10, 50, 80, 30);
		JLabel labelGhsNoRei = new JLabel("例) 1263, 1325 など");
		labelGhsNoRei.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelGhsNoRei.setBounds(190, 50, 150, 30);
		JLabel labelGhsYouki = new JLabel("容器等級");
		labelGhsYouki.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelGhsYouki.setBounds(10, 90, 80, 30);
		JLabel labelGhsYoukiRei = new JLabel("例) 2, 3 など");
		labelGhsYoukiRei.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelGhsYoukiRei.setBounds(140, 90, 150, 30);
		JLabel labelGhsName = new JLabel("国連輸送名(品名)");
		labelGhsName.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelGhsName.setBounds(10, 140, 300, 30);
		JLabel labelGhsNameRei = new JLabel("例) ペイント, その他の引火性液体, 非危険物 など");
		labelGhsNameRei.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelGhsNameRei.setBounds(10, 200, 300, 30);

		textGhsBunrui = new JTextField();
		textGhsBunrui.setBounds(70, 10, 150, 30);
		textGhsNo = new JTextField();
		textGhsNo.setBounds(70, 50, 100, 30);
		textGhsYouki = new JTextField();
		textGhsYouki.setBounds(70, 90, 50, 30);
		textGhsName = new JTextField();
		textGhsName.setBounds(10, 170, 400, 30);

		ghsPanel.add(labelGhsBunrui);
		ghsPanel.add(textGhsBunrui);
		ghsPanel.add(labelGhsBunruiRei);
		ghsPanel.add(labelGhsNo);
		ghsPanel.add(textGhsNo);
		ghsPanel.add(labelGhsNoRei);
		ghsPanel.add(labelGhsYouki);
		ghsPanel.add(textGhsYouki);
		ghsPanel.add(labelGhsYoukiRei);
		ghsPanel.add(labelGhsName);
		ghsPanel.add(textGhsName);
		ghsPanel.add(labelGhsNameRei);

		//ボタンパネルの作成
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());

		JButton buttonOk = new JButton("OK");
		JButton buttonCancel = new JButton("Cancel");

		buttonPanel.add(buttonOk, BorderLayout.WEST);
		buttonPanel.add(buttonCancel, BorderLayout.EAST);

		//buttonPanelを透明にして、contentPaneの色が見えるようにする
		buttonPanel.setOpaque(false);

		//ﾁｪｯｸﾎﾞｯｸｽniteScrapingの作成
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(null);

		checkNite = new JCheckBox(" Niteのデータをスクレイピングする");
		checkNite.setFont(new Font("MS ゴシック", Font.PLAIN, 14));
		checkNite.setBounds(10, 10, 250, 30);
		checkNite.setSelected(true);
		checkNite.setOpaque(false);
		checkBoxPanel.setOpaque(false);

		JLabel labelRenewalDeadline = new JLabel("次の日付よりﾃﾞｰﾀが古い場合");
		labelRenewalDeadline.setFont(new Font("MS ゴシック", Font.PLAIN, 12));
		labelRenewalDeadline.setBounds(30, 30, 180, 30);

		textRenewalDeadline = new JTextField("20230410");
		textRenewalDeadline.setBounds(200, 40, 70, 20);

		//ﾁｪｯｸﾎﾞｯｸｽ中文も作成する の作成
		checkChn = new JCheckBox("中文も作成する");
		checkChn.setFont(new Font("MS ゴシック", Font.PLAIN, 14));
		checkChn.setBounds(400, 10, 200, 30);
		checkChn.setOpaque(false);


		checkBoxPanel.add(checkNite);
		checkBoxPanel.add(labelRenewalDeadline);
		checkBoxPanel.add(textRenewalDeadline);
		checkBoxPanel.add(checkChn);

		//ﾊﾟﾈﾙを追加する
		tytlePanel.setBounds(0, 0, 700, 60);
		inputPanel.setBounds(10, 80, 480, 200);
		paintTypePanel.setBounds(500, 110, 120, 170);
		singleMixturePanel.setBounds(10, 300, 200, 180);
		ghsPanel.setBounds(220, 300, 450, 240);
		buttonPanel.setBounds(250, 660, 180, 30);
		checkBoxPanel.setBounds(20, 550, 600, 60);

		Container contentPane = getContentPane();
		contentPane.add(tytlePanel);
		contentPane.add(inputPanel);
		contentPane.add(paintTypePanel);
		contentPane.add(singleMixturePanel);
		contentPane.add(ghsPanel);
		contentPane.add(buttonPanel);
		contentPane.add(checkBoxPanel);

		contentPane.setBackground(new Color(255, 221, 204));

		//setActionCommandに名前を登録しておく
		mixture.addActionListener(this);
		mixture.setActionCommand("Mixture");
		single.addActionListener(this);
		single.setActionCommand("Single");

		textHinban.addActionListener(this);
		textHinban.setActionCommand("InputHinban");

		buttonOk.addActionListener(this);
		buttonOk.setActionCommand("ButtonOk");
		buttonCancel.addActionListener(this);
		buttonCancel.setActionCommand("ButtonCancel");

		ippan.setActionCommand("Ippan");
		yusei.setActionCommand("Yusei");
		metal.setActionCommand("Metal");
		bpo.setActionCommand("Bpo");

		//textRenewalDeadline.setActionCommand("RenewalDeadline");

	}

	public void actionPerformed(ActionEvent e) {
		//getActionCommandで名前を付けたActionCommandを得て
		//どのイベントなのかを調べて動く
		String cmd = e.getActionCommand();
		if (cmd.equals("Mixture")) {
			ghsPanel.setVisible(false);
		} else if (cmd.equals("Single")) {
			ghsPanel.setVisible(true);
		} else if (cmd.equals("InputHinban")) {
			String hinban = textHinban.getText().toUpperCase();
			//ｲﾝｽﾀﾝｽcgd2はｺﾝｽﾄﾗｸﾀでnew済。ここでnewすると遅いから
			String[] existData = cgd2.getExistData(hinban);

			textDisplay.setText(existData[1]);
			textSG.setText(existData[2]);
			textAppe.setText(existData[3]);
		} else if (cmd.equals("ButtonOk")) {
			//inputFrame.setVisible(false);
			boolean isInputFrame = isInputFrameOk();
			if (!isInputFrame) {
				inputFrame.setVisible(false);
				JOptionPane.showMessageDialog(
						null,
						"ｲﾝﾌﾟｯﾄﾌｫｰﾑに漏れがあります!",
						"警告!",
						JOptionPane.ERROR_MESSAGE);
				inputFrame.setVisible(true);
			} else {
				StructInputForm structInputForm = new StructInputForm();
				getStructInputForm(structInputForm);
				inputFrame.dispose(); //inputFrameを消滅する
				Controller controller = new Controller(structInputForm);
				controller.start();
			}
		} else if (cmd.equals("ButtonCancel")) {
			System.exit(1);
		}
	}

	private boolean isInputFrameOk() {
		/*
		 * buttonGroupのgetSelection()->ｸﾞﾙｰﾌﾟのどのﾗｼﾞｵﾎﾞﾀﾝが
		 * 選択されているか
		 * getSelection(),getActionCommand()->選択されている
		 * ﾗｼﾞｵﾎﾞﾀﾝのsetActionCommand()で設定した文字を返す。
		 */
		boolean isInputFrame = true;
		if (((String) comboTantou.getSelectedItem()).equals("") ||
				textHinban.getText().equals("") ||
				textDisplay.getText().equals("") ||
				textSG.getText().equals("") ||
				textAppe.getText().equals("") ||
				paintTypeGroup.getSelection() == null ||
				singleMixtureGroup.getSelection() == null ||
				(singleMixtureGroup.getSelection().getActionCommand()
						.equals("Single") &&
						(textGhsBunrui.getText().equals("") ||
								textGhsNo.getText().equals("") ||
								textGhsYouki.getText().equals("") ||
								textGhsName.getText().equals("")))) {
			isInputFrame = false;
		}
		return isInputFrame;
	}

	private void getStructInputForm(StructInputForm structInputForm) {
		structInputForm.tantou = (String) comboTantou.getSelectedItem();
		structInputForm.hinban = textHinban.getText().toUpperCase();
		structInputForm.displayName = textDisplay.getText();
		structInputForm.SG = textSG.getText();
		structInputForm.paintAppe = textAppe.getText();
		structInputForm.ghsBunrui = textGhsBunrui.getText();
		structInputForm.ghsNo = textGhsNo.getText();
		structInputForm.ghsYouki = textGhsYouki.getText();
		structInputForm.ghsName = textGhsName.getText();

		structInputForm.paintType = paintTypeGroup.getSelection()
				.getActionCommand();
		structInputForm.singleMixture = singleMixtureGroup.getSelection()
				.getActionCommand();
		structInputForm.isNiteScraping = checkNite.isSelected();
		structInputForm.renewalDeadline = textRenewalDeadline.getText();
		structInputForm.isChn = checkChn.isSelected();

	}

}
