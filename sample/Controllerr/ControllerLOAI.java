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
import sample.Model.LOAI;
import sample.Model.NHANVIEN;


import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerLOAI implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<LOAI> data;

    @FXML
    private TextField txtmatl;

    @FXML
    private TextField txtmancc;

    @FXML
    private TextField txttentl;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnsua;

    @FXML
    private Button btnthoat;

    @FXML
    private TableView<LOAI> tableloai;

    @FXML
    private TableColumn<?, ?> colummatl;

    @FXML
    private TableColumn<?, ?> colummancc;

    @FXML
    private TableColumn<?, ?> columtentl;


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
        colummatl.setCellValueFactory(new PropertyValueFactory<>("MATL"));
        colummancc.setCellValueFactory(new PropertyValueFactory<>("MANCC"));
        columtentl.setCellValueFactory(new PropertyValueFactory<>("TENTL"));
    }
    private void cleardata() {
        txtmatl.clear();
        txtmancc.clear();
        txttentl.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM LOAI");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new LOAI(rs.getString(1), rs.getString(2), rs.getString(3)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerLOAI.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableloai.setItems(data);
    }

    private void setcellvalueformtableview()    {
        tableloai.setOnMouseClicked(e->{
            LOAI p1 = tableloai.getItems().get(tableloai.getSelectionModel().getFocusedIndex());
            txtmatl.setText(p1.getMATL());
            txtmancc.setText(p1.getMANCC());
            txttentl.setText(p1.getTENTL());});
    }
    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        if(txtmatl.getText().isEmpty()== false && txtmancc.getText().isEmpty() == false&& txttentl.getText().isEmpty() == false){
        String sql = "Insert Into LOAI(MATL,MANCC,TENTL) Values(?,?,?)";
        String matl = txtmatl.getText();
        String mancc = txtmancc.getText();
        String tentl = txttentl.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, matl);
            pst.setString(2, mancc);
            pst.setString(3, tentl);

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
            Logger.getLogger(ControllerLOAI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }}else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Không được để trống!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }

        public void handleDelete(ActionEvent event) throws SQLException {
            String sql = "delete from LOAI where MATL = ?";

            try{
                pst = con.prepareStatement(sql);
                pst.setString(1,txtmatl.getText());
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
                Logger.getLogger(ControllerLOAI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public void handleUpdate(ActionEvent event) {
        if(txtmatl.getText().isEmpty()== false && txtmancc.getText().isEmpty() == false&& txttentl.getText().isEmpty() == false){
        String sql = "Update LOAI  set  MANCC = ?, TENTL=? WHERE MATL = ? ";
        try {
            String matl = txtmatl.getText();
            String mancc = txtmancc.getText();
            String tentl = txttentl.getText();

            pst = con.prepareStatement(sql);
            pst.setString(1, mancc);
            pst.setString(2, tentl);
            pst.setString(3, matl);


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
            Logger.getLogger(ControllerLOAI.class.getName()).log(Level.SEVERE, null, ex);
        }}else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Không được để trống!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void CancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }



}
