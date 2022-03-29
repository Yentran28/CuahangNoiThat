package sample.Controllerr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ControllerCTPBT;
import sample.ControllerPHIEUBAOTRI;
import sample.Model.PHIEUNHAPKHO;

import javafx.scene.control.DatePicker;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;

import javax.swing.*;

public class ControllerPHIEUNHAPKHO implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<PHIEUNHAPKHO> data;
    @FXML
    private TextField txtmapn;

    @FXML
    private TextField txtmanv;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnthoat;

    @FXML
    private TableView<PHIEUNHAPKHO> tablepnk;

    @FXML
    private TableColumn<?, ?> colmapn;

    @FXML
    private TableColumn<?, ?> colmanv;

    @FXML
    private TableColumn<?, ?> colngaylap;

    @FXML
    private DatePicker dpngaynhap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        LoadDataTableView();
        setcellvalueformtableview();
        //SearchNV();
    }
    private void setCellTable() {
        colmapn.setCellValueFactory(new PropertyValueFactory<>("MAPN"));
        colmanv.setCellValueFactory(new PropertyValueFactory<>("MANV"));
        colngaylap.setCellValueFactory(new PropertyValueFactory<>("NGAYNHAP"));


    }
    private void cleardata() {
        txtmapn.clear();
        txtmanv.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM PHIEUNHAPKHO");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new PHIEUNHAPKHO(rs.getString(1), rs.getString(2), rs.getString(3)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerPHIEUBAOTRI.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablepnk.setItems(data);
    }

    private void setcellvalueformtableview(){
        tablepnk.setOnMouseClicked(e->{
            PHIEUNHAPKHO p1 = tablepnk.getItems().get(tablepnk.getSelectionModel().getFocusedIndex());
            txtmapn.setText(p1.getMAPN());
            txtmanv.setText(p1.getMANV());
        });
    }
    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        if(txtmapn.getText().isEmpty()== false && txtmanv.getText().isEmpty() == false&& dpngaynhap.getValue() != null){
        String sql = "Insert Into PHIEUNHAPKHO(MAPN,MANV,NGAYNHAP) Values(?,?,?)";
        String pn = txtmapn.getText();
        String nv = txtmanv.getText();
        LocalDate ngaylap = dpngaynhap.getValue();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, pn);
            pst.setString(2, nv);
            pst.setString(3, String.valueOf(ngaylap));

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
            Logger.getLogger(ControllerPHIEUNHAPKHO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }}else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }

    }
    public void handleDelete(ActionEvent event) throws SQLException {
        String sql = "delete from PHIEUNHAPKHO where MAPN = ? ";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtmapn.getText());

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
}
