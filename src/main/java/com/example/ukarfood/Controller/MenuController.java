package com.example.ukarfood.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void btnFournisseurClick() {
FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ukarfood/Fournisseur.fxml"));
Parent FrnsContent;
        try {
            FrnsContent = loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
anchorPane.getChildren().setAll(FrnsContent);
    }

    @FXML
    public void btnAchatClick(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ukarfood/Achat.fxml"));
        Parent achatContent;
        try {
            achatContent = loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        anchorPane.getChildren().setAll(achatContent);
    }
    public void btnStockageClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ukarfood/Stockage.fxml"));
        Parent StockageContent;
        try {
            StockageContent = loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        anchorPane.getChildren().setAll(StockageContent);
    }
    public void btnIngredientClick() {
FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ukarfood/Ingredient.fxml"));
Parent IngredientContent ;
        try {
            IngredientContent = loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        anchorPane.getChildren().setAll(IngredientContent);


    }
    public void btnHomeClick(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ukarfood/Home.fxml"));
        Parent HomeContent ;
        try {
         HomeContent = loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        anchorPane.getChildren().setAll(HomeContent);


    }

    public void AboutUsClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ukarfood/AboutUs.fxml"));
        Parent AboutUsContent ;
        try {
            AboutUsContent = loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        anchorPane.getChildren().setAll(AboutUsContent);

    }public void initialize(){
        btnHomeClick();
    }
}

