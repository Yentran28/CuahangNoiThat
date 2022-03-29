package sample.Controllerr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.NHACUNGCAP;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerNCC implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<NHACUNGCAP> data;

    @FXML
    private TextField txtma;

    @FXML
    private TextField txttenn;

    @FXML
    private TextField txtsdt;

    @FXML
    private TextField txtdc;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnsua;

    @FXML
    private Button btnthoat;

    @FXML
    private TableColumn<?, ?> columdc;

    @FXML
    private TableView<NHACUNGCAP> tablencc;

    @FXML
    private TableColumn<?, ?> columma;

    @FXML
    private TableColumn<?, ?> columten;

    @FXML
    private TableColumn<?, ?> columsdt;



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
        columma.setCellValueFactory(new PropertyValueFactory<>("MANCC"));
        columten.setCellValueFactory(new PropertyValueFactory<>("TENNCC"));
        columsdt.setCellValueFactory(new PropertyValueFactory<>("SĐT"));
        columdc.setCellValueFactory(new PropertyValueFactory<>("DIACHI"));
    }
    private void cleardata() {
        txtma.clear();
        txttenn.clear();
        txtsdt.clear();
        txtdc.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM NHACUNGCAP");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new NHACUNGCAP(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerNCC.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablencc.setItems(data);
    }

    private void setcellvalueformtableview()    {
        tablencc.setOnMouseClicked(e->{
            NHACUNGCAP p1 = tablencc.getItems().get(tablencc.getSelectionModel().getFocusedIndex());
            txtma.setText(p1.getMANCC());
            txttenn.setText(p1.getTENNCC());
            txtsdt.setText(p1.getSĐT());
            txtdc.setText(p1.getDIACHI());});
        }


    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        if(txtma.getText().isEmpty()== false && txttenn.getText().isEmpty() == false&& txtsdt.getText().isEmpty() == false&& txtdc.getText().isEmpty() == false){
            if(checkPhone(txtsdt.getText())==true&& txtsdt.getText().length()>8 && txtsdt.getText().length()<12) {
            String sql = "Insert Into NHACUNGCAP(MANCC,TENNCC,SĐT, DIACHI) Values(?,?,?,?)";
            String ma = txtma.getText();
            String ten = txttenn.getText();
            String sdt = txtsdt.getText();
            String dc = txtdc.getText();
            try {
                pst = con.prepareStatement(sql);
                pst.setString(1, ma);
                pst.setString(2, ten);
                pst.setString(3, sdt);
                pst.setString(4, dc);

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
                Logger.getLogger(ControllerNCC.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                pst.close();
            } }else{
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
        String sql = "delete from NHACUNGCAP where MANCC = ?";

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
            Logger.getLogger(ControllerNCC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUpdate(ActionEvent event) {
        if(txtma.getText().isEmpty()== false && txttenn.getText().isEmpty() == false&& txtsdt.getText().isEmpty() == false&& txtdc.getText().isEmpty() == false){
            if(checkPhone(txtsdt.getText())==true&& txtsdt.getText().length()>8 && txtsdt.getText().length()<12){
        String sql = "Update NHACUNGCAP  set  TENNCC = ?, SĐT=?, DIACHI=? WHERE MANCC = ? ";
        try {
            String ma = txtma.getText();
            String ten = txttenn.getText();
            String sdt = txtsdt.getText();
            String dc = txtdc.getText();

            pst = con.prepareStatement(sql);

            pst.setString(1, ten);
            pst.setString(2, sdt);
            pst.setString(3, dc);
            pst.setString(4, ma);

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
            Logger.getLogger(ControllerNCC.class.getName()).log(Level.SEVERE, null, ex);
        }}else{
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
    public void CancelButtonOnAction(ActionEvent actionEvent) {
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

}
