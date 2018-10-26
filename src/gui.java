import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class gui {

	private JFrame frame;
	private JTextField textAreaInformationRetrieved;
	private JTextField textFieldLoginID;
	private JTextField textFieldStudentName;
	private JTextField data_count;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui() {
		initialize();
	}

	
	public JSONArray makeHttpRequest(String url, String method, ArrayList<NameValuePair> params) {
		InputStream is = null;
		String json = "";
		JSONArray jsnArr = null;
		
		try {
			//check for request method
			if(method == "POST") {
				//request method is POST
				//defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}else if(method == "GET") {
				//request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			
			//jObj = new JSONObject(json);
			
			jsnArr = new JSONArray(json);
			return jsnArr;
		}catch(JSONException e) {
			
		}catch(Exception ee) {
			ee.printStackTrace();
		}
		//return JSON String
		return jsnArr;
	
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data From Web Service");
		lblNewLabel.setBounds(26, 51, 130, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textAreaInformationRetrieved = new JTextField();
		textAreaInformationRetrieved.setBounds(10, 80, 414, 67);
		frame.getContentPane().add(textAreaInformationRetrieved);
		textAreaInformationRetrieved.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Student No :");
		lblNewLabel_1.setBounds(26, 177, 76, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Student Name");
		lblNewLabel_2.setBounds(26, 202, 95, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Data Count : ");
		lblNewLabel_3.setBounds(26, 227, 95, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		textFieldLoginID = new JTextField();
		textFieldLoginID.setBounds(131, 174, 86, 20);
		frame.getContentPane().add(textFieldLoginID);
		textFieldLoginID.setColumns(10);
		
		textFieldStudentName = new JTextField();
		textFieldStudentName.setBounds(131, 199, 86, 20);
		frame.getContentPane().add(textFieldStudentName);
		textFieldStudentName.setColumns(10);
		
		data_count = new JTextField();
		data_count.setBounds(131, 224, 86, 20);
		frame.getContentPane().add(data_count);
		data_count.setColumns(10);
		
		JButton btnNewButton = new JButton("Verify Answer!");
		btnNewButton.setBounds(277, 223, 112, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Get From Web Service");
		btnNewButton_1.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
	
	
	
	
				Thread searchThread = new Thread(new Runnable() {
					public void run() {
						
						ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("selectFn", "readDatabase"));
						
						
						String strUrl = "http://rku.utem.edu.my/webServiceJSON/jsonwebServices.php";
						JSONArray jArr = makeHttpRequest(strUrl,"POST",params);
						JSONObject jsnObj=null;
						String usernamee="";
						String noid="";
						String strSetText="";
						
						int femaleCount=0;
						try {
							for(int i = 0 ;i<jArr.length();i++) {
								jsnObj = jArr.getJSONObject(i);
								String userID = jsnObj.getString("USER_ID");
								String healthFacilityCode = jsnObj.getString("HEALTH_FACILITY_CODE");
								String password = jsnObj.getString("PASSWORD");
								String username = jsnObj.get("USER_NAME").toString();
								String occupationCode = jsnObj.get("OCCUPATION_CODE").toString();
								String birthDate = jsnObj.get("BIRTH_DATE").toString();
								String sexCode = jsnObj.get("SEX_CODE").toString();
								if(sexCode.equalsIgnoreCase("Female"))
									femaleCount++;
								String newICNo = jsnObj.get("NEW_ICNO").toString();
								String homePhone = jsnObj.get("HOME_PHONE").toString();
								String officePhone = jsnObj.get("OFFICE_PHONE").toString();
								String mobilePhone = jsnObj.get("MOBILE_PHONE").toString();
								String loginStatus = jsnObj.get("LOGIN_STATUS").toString();
								String userStatus = jsnObj.get("USER_STATUS").toString();
								String email = jsnObj.get("E_MAIL").toString();
								String idCategoryCode = jsnObj.get("ID_CATEGORY_CODE").toString();
								String startDate = jsnObj.get("START_DATE").toString();
								String endDate = jsnObj.get("END_DATE").toString();
								String roomNo = jsnObj.get("ROOM_NO").toString();
								String hfcCd = jsnObj.get("hfc_cd").toString();
								strSetText += "User ID :"+userID+
										" || Health Facility Code :"+healthFacilityCode+
										" || Password :"+password+
										" || Username :"+username+
										" || Password :"+occupationCode+
										" || Birth Date :"+birthDate+
										" || Sex Code :"+sexCode+
										" || New IC No :"+newICNo+
										" || Home Phone :"+homePhone+
										" || Office Phone :"+officePhone+
										" || Mobile Phone :"+mobilePhone+
										" || Login Status :"+loginStatus+
										" || User Status :"+userStatus+
										" || Email :"+email+
										" || ID Category Code :"+idCategoryCode+
										" || Start Date :"+startDate+
										" || End Date :"+endDate+
										" || Room No :"+roomNo+
										" || hfc cd :"+hfcCd+"\n";
								usernamee += ""+username;
								noid +=""+userID;
							}
							textFieldLoginID.setText(noid);
							textFieldStudentName.setText(usernamee);
							textAreaInformationRetrieved.setText(strSetText);
							data_count.setText(String.valueOf(femaleCount));
					
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
					public JSONArray makeHttpRequest(String strUrl, String method, ArrayList<NameValuePair> params) {
						InputStream is = null;
						String json = "";
						JSONArray jArr = null;
						
						try {
							if(method == "POST") {
								DefaultHttpClient httpClient = new DefaultHttpClient();
								HttpPost httpPost = new HttpPost(strUrl);
								httpPost.setEntity(new UrlEncodedFormEntity(params));
								HttpResponse httpResponse = httpClient.execute(httpPost);
								HttpEntity httpEntity = httpResponse.getEntity();
								is = httpEntity.getContent();
							}
							else if(method == "GET") {
								DefaultHttpClient httpClient = new DefaultHttpClient();
								String paramString = URLEncodedUtils.format(params, "utf-8");
								strUrl+="?"+paramString;
								HttpGet httpGet = new HttpGet(strUrl);
								
								HttpResponse httpResponse = httpClient.execute(httpGet);
								HttpEntity httpEntity = httpResponse.getEntity();
								is = httpEntity.getContent();
							}
							
							BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
							StringBuilder sb = new StringBuilder();
							String line = null;
							while((line = reader.readLine())!=null) 
								sb.append(line+"\n");
							is.close();
							json = sb.toString();
							jArr = new JSONArray(json);
							
						}	catch(JSONException e) {
							try {
								jArr = new JSONArray(json);
							}catch(JSONException e1) {
								e1.printStackTrace();
							}
						}	catch (Exception ee) {
							ee.printStackTrace();
						}
						return jArr;
					}
				});
				
				searchThread.start();
				
				
				
			}
		});
		btnNewButton_1.setBounds(161, 11, 158, 23);
		frame.getContentPane().add(btnNewButton_1);
	}
}
