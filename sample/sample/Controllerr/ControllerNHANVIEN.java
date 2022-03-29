package sample.Controllerr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.NHANVIEN;

import javax.swing.*;

public class ControllerNHANVIEN implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<NHANVIEN> data;

    @FXML
    private TableView<NHANVIEN> tablenhanvien;

    @FXML
    private TableColumn<?, ?> columma;

    @FXML
    private TableColumn<?, ?> columten;

    @FXML
    private TableColumn<?, ?> columtk;

    @FXML
    private TableColumn<?, ?> colummk;

    @FXML
    private TableColumn<?, ?> columsdt;

    @FXML
    private TableColumn<?, ?> columdiachi;

    @FXML
    private TableColumn<?, ?> columemail;

    @FXML
    private TextField txtma;

    @FXML
    private TextField txtten;

    @FXML
    private TextField txttk;

    @FXML
    private TextField txtmk;

    @FXML
    private TextField txtsdt;

    @FXML
    private TextField txtdiachi;

    @FXML
    private TextField txtemail;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnsua;

    @FXML
    private Button btnthoat;

    @FXML
    private TextField txtsearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        LoadDataTableView();
        setcellvalueformtableview();
        SearchNV();
    }
    private void setCellTable() {
        columma.setCellValueFactory(new PropertyValueFactory<>("MANV"));
        columten.setCellValueFactory(new PropertyValueFactory<>("TENNV"));
        columtk.setCellValueFactory(new PropertyValueFactory<>("TAIKHOAN"));
        colummk.setCellValueFactory(new PropertyValueFactory<>("MATKHAU"));
        columsdt.setCellValueFactory(new PropertyValueFactory<>("SĐT"));
        columdiachi.setCellValueFactory(new PropertyValueFactory<>("DIACHI"));
        columemail.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
    }
    private void cleardata() {
        txtma.clear();
        txtten.clear();
        txttk.clear();
        txtmk.clear();
        txtsdt.clear();
        txtdiachi.clear();
        txtemail.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM NHANVIEN");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new NHANVIEN(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablenhanvien.setItems(data);

    }

    public void CancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAddNhanVien(ActionEvent event) throws SQLException {
        if(txtma.getText().isEmpty()== false && txtten.getText().isEmpty() == false&& txttk.getText().isEmpty() == false&& txtmk.getText().isEmpty() == false&& txtsdt.getText().isEmpty() == false&& txtdiachi.getText().isEmpty() == false&& txtemail.getText().isEmpty() == false){
        String sql = "Insert Into NHANVIEN(MANV,TENNV,TAIKHOAN,MATKHAU,SĐT,DIACHI,EMAIL) Values(?,?,?,?,?,?,?)";
        String ma = txtma.getText();
        String ten = txtten.getText();
        String tk = txttk.getText();
        String mk = txtmk.getText();
        String sdt = txtsdt.getText();
        String diachi = txtdiachi.getText();
        String email = txtemail.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, ma);
            pst.setString(2, ten);
            pst.setString(3, tk);
            pst.setString(4, mk);
            pst.setString(5, sdt);
            pst.setString(6, diachi);
            pst.setString(7, email);

            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV =new Alert(Alert.AlertType.WARNING,"Thêm Thành Công",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();
            }
        } catch (SQLException ex) {
            Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }}else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }

    public void handleUpdateNhanVien(ActionEvent event) {
        if(txtma.getText().isEmpty()== false && txtten.getText().isEmpty() == false&& txttk.getText().isEmpty() == false&& txtmk.getText().isEmpty() == false&& txtsdt.getText().isEmpty() == false&& txtdiachi.getText().isEmpty() == false&& txtemail.getText().isEmpty() == false){
        String sql = "Update NHANVIEN  set  TENNV = ?, TAIKHOAN = ?, MATKHAU = ?, DIACHI = ?, SĐT = ?, EMAIL=? WHERE MANV = ? ";
        try {
            String ma = txtma.getText();
            String ten = txtten.getText();
            String tk = txttk.getText();
            String mk = txtmk.getText();
            String sdt = txtsdt.getText();
            String diachi = txtdiachi.getText();
            String email = txtemail.getText();
            pst = con.prepareStatement(sql);
            pst.setString(1, ten);
            pst.setString(2, tk);
            pst.setString(3, mk);
            pst.setString(4, sdt);
            pst.setString(5, diachi);
            pst.setString(6, email);
            pst.setString(7, ma);

            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV =new Alert(Alert.AlertType.WARNING,"Sửa Thành Công",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();

            }
        } catch (SQLException ex) {
            Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
        }}else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void handleDeleteNhanVien(ActionEvent event) throws SQLException {
        String sql = "delete from NHANVIEN where MANV = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtma.getText());
            int i=  pst.executeUpdate();
            if(i==1){
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
            Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setcellvalueformtableview()    {
        tablenhanvien.setOnMouseClicked(e->{
            NHANVIEN p1 = tablenhanvien.getItems().get(tablenhanvien.getSelectionModel().getFocusedIndex());
            txtma.setText(p1.getMANV());
            txtten.setText(p1.getTENNV());
            txttk.setText(p1.getTAIKHOAN());
            txtmk.setText(p1.getMATKHAU());
            txtsdt.setText(p1.getSĐT());
            txtdiachi.setText(p1.getDIACHI());
            txtemail.setText(p1.getEMAIL()); });
    }

    private void SearchNV()
    {
        txtsearch.setOnKeyReleased(e->{
            if(txtsearch.getText().equals(""))
            {
                LoadDataTableView();
            }
            else {
                data.clear();
                String sql ="Select *from NHANVIEN Where MANV LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from NHANVIEN Where TENNV LIKE N'%"+txtsearch.getText()+"%' "
                        + "UNION Select *from NHANVIEN Where TAIKHOAN LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from NHANVIEN Where MATKHAU LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from NHANVIEN Where SĐT LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from NHANVIEN Where DIACHI LIKE N'%"+txtsearch.getText()+"%'"
                        + "UNION Select *from NHANVIEN Where EMAIL LIKE '%"+txtsearch.getText()+"%'";
                try {
                    pst =con.prepareStatement(sql);

                    rs=pst.executeQuery();
                    while (rs.next())
                    {
                        //System.out.println(""+rs.getString(1));
                        data.add(new NHANVIEN(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
                    }
                    tablenhanvien.setItems(data);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }
}
