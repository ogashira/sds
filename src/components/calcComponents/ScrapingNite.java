package components.calcComponents;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import structures.StructNite;
import tools.ToFloat;




public class ScrapingNite {

	private List<String[]> elementList;
	private StructNite structNite;

	public ScrapingNite(String item) {

		structNite = new StructNite();
		structNite.casNo = item;
		structNite.kasinhouNo = "null";
		structNite.kasinhouType = "null";
		structNite.prtrNo4 = "null";
		structNite.prtrNo5 = "null";
		structNite.prtrBunrui4 = "null";
		structNite.prtrBunrui5 = "null";
		structNite.aneihouNo = "null";
		structNite.rangeOfDisplay = 9999;
		structNite.rangeOfNotification = 9999;
		structNite.tokkasokuKubun = "null";
		structNite.tokkasokuNo = "null";
		structNite.tokkasokuTargetRange = 9999;
		structNite.yuukisokuKubun = "null";
		structNite.yuukisokuNo = "null";
		structNite.kanriNoudo = "null";
		structNite.dokugekiBunrui = "null";
		structNite.dokugekiNo = "null";
		structNite.unNo = "null";
		structNite.unClass = "null";
		structNite.unName = "null";
		structNite.hsCode = "null";
		structNite.niSsanEiPpm = 9999;
		structNite.niSsanEiMgParM3 = 9999;
		structNite.update = "null";
		structNite.isSuccess = true;

		Elements elements = null;
		elementList = new ArrayList<>();

		this.disableSSLValidation();

		try {

			Document doc = Jsoup.connect("https://www.chem-info.nite.go.jp/chem/"
					+ "chrip/chrip_search/srhChripIdLst?_e_trans=&shMd"
					+ "=0&txNumSh=" + item + "&ltNumTp=1&bcPtn=5")
					.timeout(300000).get();

			elements = doc.select("li");

			for (Element element : elements) {
				String[] line = element.text().split(" ");
				elementList.add(line);
			}
		} catch (NullPointerException e) {
			structNite.isSuccess = false;
			System.out.println("nullです->ScrapintNite");
			e.printStackTrace();
		} catch (java.io.IOException e) {
			structNite.isSuccess = false;
			System.out.println("niteからのﾃﾞｰﾀ抜き失敗です:" + item);
			e.printStackTrace();
		}
	}

	public List<String[]> getElementList() {
		return elementList;
	}

	public void addKasinhouNo() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("化審法：既存化学物質")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("化審法官報整理番号")) {
							if (structNite.kasinhouNo.equals("null")) {
								structNite.kasinhouNo = line[i + 1].replace(",", " ");
							} else {
								structNite.kasinhouNo = structNite.kasinhouNo + " " + line[i + 1]
										.replace(",", " ");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("化審法No取得中に例外発生しました！");
		}
	}

	public void addKasinhouType() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("化審法：優先評価化学物質")) {
					structNite.kasinhouType = "優先評価化学物質";
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("優先評価化学物質取得中に例外発生しました！");
		}
	}

	public void addPrtrNo4() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("化管法") && line[1].contains("(令和４年度分までの")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("政令番号")) {
							structNite.prtrNo4 = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("化管法政令番号(令和4年度)取得中に例外発生しました！");
		}
	}

	public void addPrtrNo5() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("化管法") && line[1].contains("(令和５年度分以降の")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("政令番号")) {
							structNite.prtrNo5 = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("化管法政令番号(令和5年度以降)取得中に例外発生しました！");
		}
	}

	public void addPrtrBunrui4() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("化管法") && line[1].contains("(令和４年度分までの")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("分類")) {
							structNite.prtrBunrui4 = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("化管法分類(令和4年度)取得中に例外発生しました！");
		}
	}

	public void addPrtrBunrui5() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("化管法") && line[1].contains("(令和５年度分以降の")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("分類")) {
							structNite.prtrBunrui5 = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("化管法分類(令和5年度以降)取得中に例外発生しました！");
		}
	}

	public void addAneihouNo() {
		try {
			for (String[] line : elementList) {
				if (line[0].contains("安衛法：名称等を表示し、")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("政令番号")) {
							structNite.aneihouNo = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("安衛法政令番号取得中に例外発生しました！");
		}
	}

	public void addRangeOfDisplay() {
		try {
			for (String[] line : elementList) {
				if (line[0].contains("安衛法：名称等を表示し、")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("表示の対象となる範囲（重量％）")) {
							if (line[i + 1].equals("すべて")) {
								structNite.rangeOfDisplay = 0;
							} else {
								structNite.rangeOfDisplay = ToFloat.getToFloat(line[i + 1]);
							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			structNite.isSuccess = false;
			System.out.println("安衛法表示閾値取得中にToFloatクラスの"
					+ "NumberFormatExceptionをｷｬｯﾁしました!oga");
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("安衛法表示閾値取得中に例外発生しました！");
		}
	}

	public void addRangeOfNotification() {
		try {
			for (String[] line : elementList) {
				if (line[0].contains("安衛法：名称等を表示し、")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("通知の対象となる範囲（重量％）")) {
							if (line[i + 1].equals("すべて")) {
								structNite.rangeOfNotification = 0;
							} else {
								structNite.rangeOfNotification = ToFloat.getToFloat(line[i + 1]);
							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			structNite.isSuccess = false;
			System.out.println("安衛法通知閾値取得中にToFloatクラスの"
					+ "NumberFormatExceptionをｷｬｯﾁしました!oga");
		} catch (Exception e) {
			structNite.isSuccess = true;
			System.out.println("安衛法通知閾値取得中に例外発生しました！");
		}
	}

	public void addTokkasokuKubun() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("安衛法：特定化学物質等（特化則）")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("区分")) {
							structNite.tokkasokuKubun = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("特化則区分取得中に例外発生しました！");
		}
	}

	public void addTokkasokuNo() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("安衛法：特定化学物質等（特化則）")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("政令番号")) {
							structNite.tokkasokuNo = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("特化則政令番号取得中に例外発生しました！");
		}
	}

	public void addTokkasokuTargetRange() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("安衛法：特定化学物質等（特化則）")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("対象となる範囲（重量％）")) {
							if (line[i + 1].equals("すべて")) {
								structNite.tokkasokuTargetRange = 0;
							} else {
								structNite.tokkasokuTargetRange = ToFloat.getToFloat(line[i + 1]);
							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			structNite.isSuccess = false;
			System.out.println("特化則閾値取得中にToFloatクラスの"
					+ "NumberFormatExceptionをｷｬｯﾁしました!oga");
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("特化則閾値取得中に例外発生しました！");
		}
	}

	public void addYuukisokuKubun() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("安衛法：有機溶剤等（有機則）")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("区分")) {
							structNite.yuukisokuKubun = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("有機則区分取得中に例外発生しました！");
		}
	}

	public void addYuukisokuNo() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("安衛法：有機溶剤等（有機則）")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("政令番号")) {
							structNite.yuukisokuNo = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("有機則政令番号取得中に例外発生しました！");
		}
	}

	public void addKanriNoudo() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("安衛法：作業環境評価基準で定める管理濃度")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("管理濃度")) {
							structNite.kanriNoudo = ToFloat.zenToHan(line[i + 1].replace(",", " "));
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("安衛法管理濃度取得中に例外発生しました！");
		}
	}

	public void addDokugekiBunrui() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("毒物及び劇物取締法")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("分類")) {
							structNite.dokugekiBunrui = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("毒劇物分類取得中に例外発生しました！");
		}
	}

	public void addDokugekiNo() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("毒物及び劇物取締法")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("法律又は政令番号")) {
							structNite.dokugekiNo = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("毒劇物政令番号取得中に例外発生しました！");
		}
	}

	public void addUnNumber() {
		/*
		 * uvNoは２個以上情報がある場合がある。G-NFなど。
		 * unNameは一番目の情報と決めたので、unNo,unClassも一番目の
		 * 情報を取る。情報取ったら、breakで抜ける。
		 * 他はbreakを入れてないので情報が２つある場合は、最後の情報
		 * を取ることになる。
		 */
		try {
			for (String[] line : elementList) {
				if (line[0].equals("危険物リスト（国連番号／危険分類）")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("国連番号")) {
							structNite.unNo = line[i + 1].replace(",", " ");
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("国連番号取得中に例外発生しました！");
		}
	}

	public void addUnClass() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("危険物リスト（国連番号／危険分類）")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("Division")) {
							structNite.unClass = line[i + 1].replace(",", " ");
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("国連クラス取得中に例外発生しました！");
		}
	}

	public void addUnName() {
		/*
		 *METYL ETYL KETONE など、空白のある文字は切れてしまうので、
		 *"Description"の後から最後までを繋げて文字にする。
		 *しかし、unNameにはｶﾝﾏが入っている場合があるので、replace(","," ")
		 *にしてから繋げる。
		 *また、G-NFの場合は、国連番号、ｸﾗｽ、名前が２回出てくる。すると、
		 *unNameを取るときに２番目に出てくる情報が全部つながってしまうので、
		 *一番最初の情報を取ることにした。descriptionの後の文字を繋げていって、
		 *２番目の情報[参考和訳]が出たら、forを抜ける。escapeをtrueにして
		 *上の階層のforも次々抜けるようにした。
		 * */
		boolean escape = false;
		try {
			for (String[] line : elementList) {
				if (escape) {
					break;
				}
				if (line[0].equals("危険物リスト（国連番号／危険分類）")) {
					for (int i = 0; i < line.length; i++) {
						if (escape) {
							break;
						}
						if (line[i].equals("Description")) {
							for (int j = i + 1; j < line.length; j++) {
								if (line[j].equals("参考和訳")) {
									escape = true;
									break;
								}
								if (structNite.unName.equals("null")) {
									structNite.unName = line[j].replace(",", " ");
								} else {
									structNite.unName = structNite.unName + " " + line[j].replace(",", " ");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("国連危険品名取得中に例外発生しました！");
		}
	}

	public void addHsCode() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("HSコード")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("HSコード")) {
							structNite.hsCode = line[i + 1].replace(",", " ");
						}
					}
				}
			}
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("HSコード取得中に例外発生しました！");
		}
	}

	public void addNiSanEiPpm() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("日本産業衛生学会：許容濃度")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("許容濃度（ppm）")) {
							if (line[i + 1].equals("-")) {
								structNite.niSsanEiPpm = 9999;
							} else {
								structNite.niSsanEiPpm = ToFloat.getToFloat(line[i + 1]);
							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			structNite.isSuccess = false;
			System.out.println("日本産業衛生学会ppm取得中にToFloatクラスの"
					+ "NumberFormatExceptionをｷｬｯﾁしました!oga");
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("日本産業衛生学会ppm取得中に例外発生しました！");
		}
	}

	public void addNiSanEiMgParM3() {
		try {
			for (String[] line : elementList) {
				if (line[0].equals("日本産業衛生学会：許容濃度")) {
					for (int i = 0; i < line.length; i++) {
						if (line[i].equals("許容濃度（mg/m3）")) {
							structNite.niSsanEiMgParM3 = ToFloat.getToFloat(line[i + 1]);
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			structNite.isSuccess = false;
			System.out.println("日本産業衛生学会mg/m3取得中にToFloatクラスの"
					+ "NumberFormatExceptionをｷｬｯﾁしました!oga");
		} catch (Exception e) {
			structNite.isSuccess = false;
			System.out.println("日本産業衛生学会mg/m3取得中に例外発生しました！");
		}
	}

	public void addUpdate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd");
		String update = dtf.format(cal.getTime());
		structNite.update = update;
	}

	public StructNite getStructNite() {
		this.addKasinhouNo();
		this.addKasinhouType();
		this.addPrtrNo4();
		this.addPrtrNo5();
		this.addPrtrBunrui4();
		this.addPrtrBunrui5();
		this.addAneihouNo();
		this.addRangeOfDisplay();
		this.addRangeOfNotification();
		this.addTokkasokuKubun();
		this.addTokkasokuNo();
		this.addTokkasokuTargetRange();
		this.addYuukisokuKubun();
		this.addYuukisokuNo();
		this.addKanriNoudo();
		this.addDokugekiBunrui();
		this.addDokugekiNo();
		this.addUnNumber();
		this.addUnClass();
		this.addUnName();
		this.addHsCode();
		this.addNiSanEiPpm();
		this.addNiSanEiMgParM3();
		this.addUpdate();

		return structNite;
	}

	private void disableSSLValidation() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
            };

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            // Create an all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
