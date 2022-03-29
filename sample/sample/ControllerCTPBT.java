package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.CTPBT;

import javafx.scene.control.ComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import sample.Model.PHIEUBAOTRI;

import javax.swing.*;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerCTPBT implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<CTPBT> data;


    @FXML
    private TextField txtmabt;

    @FXML
    private TextField txtdg;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private TextField txtsl;

    @FXML
    private Button btnthoat;

    @FXML
    private TableView<CTPBT> tablectpbt;

    @FXML
    private TableColumn<?, ?> colmasp;

    @FXML
    private TableColumn<?, ?> colmabt;

    @FXML
    private TableColumn<?, ?> coldg;

    @FXML
    private TableColumn<?, ?> colsl;

    @FXML
    private ComboBox<String> cbomasp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        loadcbo();
        setCellTable();
        LoadDataTableView();
        setcellvalueformtableview();
        //SearchNV();
    }


    private void setCellTable() {
        colmasp.setCellValueFactory(new PropertyValueFactory<>("MASP"));
        colmabt.setCellValueFactory(new PropertyValueFactory<>("MABT"));
        coldg.setCellValueFactory(new PropertyValueFactory<>("DONGIABT"));
        colsl.setCellValueFactory(new PropertyValueFactory<>("SLBT"));

    }
    private void cleardata() {
        txtmabt.clear();
        txtsl.clear();
        txtdg.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM CTPBT");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new CTPBT(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerCTPBT.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablectpbt.setItems(data);
    }

    private void setcellvalueformtableview(){
        tablectpbt.setOnMouseClicked(e->{
            CTPBT p1 = tablectpbt.getItems().get(tablectpbt.getSelectionModel().getFocusedIndex());
            cbomasp.setValue(p1.getMASP());
            txtmabt.setText(p1.getMABT());
            txtdg.setText(p1.getDONGIABT());
            txtsl.setText(p1.getSLBT());
        });
    }
    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        if(cbomasp.getValue().isEmpty()== false && txtmabt.getText().isEmpty() == false&& txtsl.getText().isEmpty() == false){
            if(checkPhone(txtdg.getText())==true && checkPhone(txtsl.getText())==true){
        String sql = "Insert Into CTPBT(MASP,MABT,DONGIABT,SLBT) Values(?,?,?,?)";
        String sp = cbomasp.getValue();
        String bt = txtmabt.getText();
        String dg = txtdg.getText();
        String sl = txtsl.getText();
        //String ngay = txtngaybt.getPromptText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, sp);
            pst.setString(2, bt);
            pst.setString(3, dg);
            pst.setString(4, sl);
            //pst.setString(5, ngay);

            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV = new Alert(Alert.AlertType.WARNING, "Thêm Thành Công", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();
            }
        } catch (SQLException ex) {
            Alert VV = new Alert(Alert.AlertType.WARNING, " Lỗi Nhập Liệu", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerCTPBT.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }
        }else{
                Alert VV = new Alert(Alert.AlertType.WARNING, " Số điện thoại không hợp lệ!", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
            }
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void handleDelete(ActionEvent event) throws SQLException {
        String sql = "delete from CTPBT where MASP = ? and MABT=? ";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,cbomasp.getValue());
            pst.setString(2,txtmabt.getText());

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
            Logger.getLogger(ControllerCTPBT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }
    private void loadcbo(){
        try{
            List<String> options = new ArrayList<>();
            pst = con.prepareStatement("select MASP \n" +
                    "from CTHD\n" +
                    "where MAHD=(\n" +
                    "select top (1) MAHD\n" +
                    "from PHIEUBAOTRI\n" +
                    "order by MABT DESC\n" +
                    ")");
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
}

