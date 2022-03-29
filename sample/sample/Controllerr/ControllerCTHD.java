package sample.Controllerr;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ControllerHOADON;
import sample.Model.CTHD;


import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerCTHD implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<CTHD> data;

    @FXML
    private TextField txtmahd;

    @FXML
    private TextField txtsl;

    @FXML
    private TextField txtdg;

    @FXML
    private TableView<CTHD> tablecthd;

    @FXML
    private TableColumn<?, ?> colmahd;

    @FXML
    private TableColumn<?, ?> colmasp;

    @FXML
    private TableColumn<?, ?> colsl;

    @FXML
    private TableColumn<?, ?> coldg;

    @FXML
    private Button btnthoat;

    @FXML
    private ComboBox<String> cbomasp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        loadtxtmahd();
        loadcbo();
        setCellTable();
        LoadDataTableView();
        setcellvalueformtableview();
        //SearchNV();
    }
    private void setCellTable() {
        colmahd.setCellValueFactory(new PropertyValueFactory<>("MAHD"));
        colmasp.setCellValueFactory(new PropertyValueFactory<>("MASP"));
        colsl.setCellValueFactory(new PropertyValueFactory<>("SL"));
        coldg.setCellValueFactory(new PropertyValueFactory<>("DONGIA"));
    }
    private void cleardata() {
        txtmahd.clear();
        txtsl.clear();
        txtdg.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM CTHD order by MAHD ASC");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new CTHD(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerCTHD.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablecthd.setItems(data);
    }

    private void setcellvalueformtableview(){
        tablecthd.setOnMouseClicked(e->{
            CTHD p1 = tablecthd.getItems().get(tablecthd.getSelectionModel().getFocusedIndex());
            txtmahd.setText(p1.getMAHD());
            cbomasp.setValue(p1.getMASP());
            txtsl.setText(p1.getSL());
            //txtdg.setText(p1.getDONGIA());
            });
    }
    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        if(txtmahd.getText().isEmpty()== false && cbomasp.getValue().isEmpty() == false&& txtsl.getText().isEmpty() == false){
            if(checkPhone(txtsl.getText())==true){
        String sql = "Insert Into CTHD(MAHD,MASP,SL,DONGIA) Values(?,?,?,?)";
        String hd = txtmahd.getText();
        String sp = cbomasp.getValue();
        String sl = txtsl.getText();
        //String dg = txtdg.getText();

        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("select GIA from SANPHAM where MASP = N'"+cbomasp.getValue()+"'");
            while(rs.next()) {
                String dg = rs.getString(1);

            pst = con.prepareStatement(sql);
            pst.setString(1, hd);
            pst.setString(2, sp);
            pst.setString(3, sl);
            pst.setString(4, dg);

            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV = new Alert(Alert.AlertType.WARNING, "Thêm Thành Công", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();
                loadtxtmahd();
            }}
        } catch (SQLException ex) {
            Alert VV = new Alert(Alert.AlertType.WARNING, " Lỗi Nhập Liệu", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerCTHD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }}else{
                Alert VV = new Alert(Alert.AlertType.WARNING, " Số điện thoại không hợp lệ!", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
            }
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Không được để trống!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void handleDelete(ActionEvent event) throws SQLException {
        String sql = "delete from CTHD where MAHD = ? and MASP=? and SL=? ";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtmahd.getText());
            pst.setString(2,cbomasp.getValue());
            pst.setString(3,txtsl.getText());
            //pst.setString(4,txtdg.getText());
            int i=  pst.executeUpdate();
            if(i==1 || i==2){
                System.out.println("Data Delete Successfully");
                LoadDataTableView();
                Alert VV =new Alert(Alert.AlertType.WARNING,"Xóa Thành Công",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();

            }
        }catch(SQLException ex){
            Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerCTHD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void handlecancel(ActionEvent event) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }
    public static boolean checkPhone(String value) {

        try {
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    private void loadtxtmahd(){
        try{
            List<String> options = new ArrayList<>();
            pst = con.prepareStatement("select top(1) MAHD \n" +
                    "from HOADON\n" +
                    "order by MAHD DESC");
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("MAHD");
                //options.add(name);
                txtmahd.setText(name);
            }
        }catch(SQLException ex){
            Logger.getLogger(ControllerHOADON.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    private void loadcbo(){
        try{
            List<String> options = new ArrayList<>();
            pst = con.prepareStatement("select MASP from SANPHAM");
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("MASP");
                options.add(name);
            }
            cbomasp.setItems(observableArrayList(options));
        }catch(SQLException ex){
            Logger.getLogger(ControllerHOADON.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
