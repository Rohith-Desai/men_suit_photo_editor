package com.hangoverstudios.men.photo.suite.editor.app.billing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.android.billingclient.api.BillingClient.SkuType.INAPP;
import static com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods.commonMethods;
import static com.hangoverstudios.men.photo.suite.editor.app.billing.Security.verifyPurchase;


public class BillingController implements PurchasesUpdatedListener {
    public static final String PREF_FILE = "MEN_SUIT";
    public static final String PURCHASE_KEY = "removeads";
    String PRODUCT_ID = "removeads";
    Context mContext;
    UpdateBilling updateBilling;
    AcknowledgePurchaseResponseListener ackPurchase = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                //if purchase is acknowledged
                // Grant entitlement to the user. and restart activity
                savePurchaseValueToPref(true);
                if(updateBilling != null){
                    updateBilling.updateUI();
                }
                Toast.makeText(mContext, "Item Purchased", Toast.LENGTH_SHORT).show();
//                MainActivity.this.recreate(); // need to recreate the activity...,

            }
        }
    };
    private BillingClient billingClient;

    public BillingController(Context mContext) {
        this.mContext = mContext;
    }
    public BillingController(Context mContext,UpdateBilling updateBilling) {
        this.mContext = mContext;
        this.updateBilling = updateBilling;
    }

    public void billingInitialization() {
        billingClient = BillingClient.newBuilder(mContext)
                .enablePendingPurchases().setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Purchase.PurchasesResult queryPurchase = billingClient.queryPurchases(INAPP);
                    List<Purchase> queryPurchases = queryPurchase.getPurchasesList();
                    if (queryPurchases != null && queryPurchases.size() > 0) {
                        handlePurchases(queryPurchases);
                    }
                    //if purchase list is empty that means item is not purchased
                    //Or purchase is refunded or canceled
                    else {
                        savePurchaseValueToPref(false);
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });
    }

    private SharedPreferences getPreferenceObject() {
        return mContext.getSharedPreferences(PREF_FILE, 0);
    }

    private SharedPreferences.Editor getPreferenceEditObject() {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_FILE, 0);
        return pref.edit();
    }

    public boolean getPurchaseValueFromPref() {
        return getPreferenceObject().getBoolean(PURCHASE_KEY, false);
    }

    private void savePurchaseValueToPref(boolean value) {
        getPreferenceEditObject().putBoolean(PURCHASE_KEY, value).commit();
    }

    public void handlePurchases(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            //if item is purchased
            if (PRODUCT_ID.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                    // Invalid purchase
                    // show error to user
                    Toast.makeText(mContext, "Error : Invalid Purchase", Toast.LENGTH_SHORT).show();
                    return;
                }
                // else purchase is valid
                //if item is purchased and not acknowledged
                if (!purchase.isAcknowledged()) {
                    AcknowledgePurchaseParams acknowledgePurchaseParams =
                            AcknowledgePurchaseParams.newBuilder()
                                    .setPurchaseToken(purchase.getPurchaseToken())
                                    .build();
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams, ackPurchase);
                }
                //else item is purchased and also acknowledged
                else {
                    // Grant entitlement to the user on item purchase
                    // restart activity
                    if (!getPurchaseValueFromPref()) {
                        savePurchaseValueToPref(true);
//                        Toast.makeText(getApplicationContext(), "Item Purchased", Toast.LENGTH_SHORT).show();
                        commonMethods.disableAds();
                        if(updateBilling != null){
                            updateBilling.updateUI();
                        }
//                        remove_ads.setVisibility(View.GONE);  // need to recreate the activity.
//                        this.recreate();
                    }
                }
            }
            //if purchase is pending
            else if (PRODUCT_ID.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                Toast.makeText(mContext,
                        "Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT).show();
            }
            //if purchase is unknown
            else if (PRODUCT_ID.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                savePurchaseValueToPref(false);

                Toast.makeText(mContext, "Purchase Status Unknown", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            //String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgB3x5gGrrf2A/ud90vic6SnDJr16619zjA93t3GOBz4BkYWywt4CyqZnyYDggbnPkuiDE/rGww2npBLhzyH0+ZqPJul42JCNx4dZLdao9uOd9/gB87m6oO2I5/3waE/0U2DV1+AHB8nl/qzOMfwOmkcdiJ1y9/dm0sk/FGjnnTkrwrCvXDO/PY/H8byM6IAswAiMtYR2nB162SOFOm3HS9gpJHrPeJHjLgad76ixo8qfZDL2E49FwpeVg8CdL1edMpmEjy1pvM0j/4IkJnJnN2+Cm2vISNZLBhrV/j5dGPbyiz7Jdy5HaVkLftPPzdDm+GoMZQN9jnG0kB7rQ/xVnwIDAQAB";
            String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgixDAAKzsCSxt55oO+pBUpFlDtOwHGmC8uS4ZxUckRBAhAblGVSRUuzOoxy5Aiosr0Sr8lgCW4I05Xvzaie+eD1292qd0R3RShfj/RVOF+CCDZ64rD7twIFscwKmH867d4ftDD6c6TEIdcAai6EKf4izLVdM8K3jOTGi0SLUydeeF89N8by6B127mXumRO3jKFNcwEaKeEDPdyfWdiHcpMne+WWRaTpEQMGPIm9+vut+toUA8qqvk8yBOb1++P4n+jsAiP/lih8h2moULFSSNGjtq4vUrMbe+dlVoQPkBEQQuiHBblFRb2iXReOH3aVBJ3Jy4rncVWnWbIrcuJ4djwIDAQAB";//new
            return verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            return false;

        }

    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {

        //if item newly purchased
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases);
        }
        //if item already purchased then check and reflect changes
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            Purchase.PurchasesResult queryAlreadyPurchasesResult = billingClient.queryPurchases(INAPP);
            List<Purchase> alreadyPurchases = queryAlreadyPurchasesResult.getPurchasesList();
            if (alreadyPurchases != null) {
                handlePurchases(alreadyPurchases);
            }
        }
        //if purchase cancelled
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(mContext, "Purchase Canceled", Toast.LENGTH_SHORT).show();
        }
        // Handle any other error msgs
        else {
            Toast.makeText(mContext, "Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void showPurchaseDialog() {
        //check if service is already connected
        if (billingClient.isReady()) {
            initiatePurchase();
        }
        //else reconnect service
        else {
            billingClient = BillingClient.newBuilder(mContext).enablePendingPurchases().setListener(this).build();
            billingClient.startConnection(new BillingClientStateListener() {
                @Override
                public void onBillingSetupFinished(BillingResult billingResult) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        initiatePurchase();
                    } else {
                        Toast.makeText(mContext, "Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onBillingServiceDisconnected() {
                }
            });
        }
    }

    private void initiatePurchase() {
        List<String> skuList = new ArrayList<>();
        skuList.add(PRODUCT_ID);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            if (skuDetailsList != null && skuDetailsList.size() > 0) {
                                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                        .setSkuDetails(skuDetailsList.get(0))
                                        .build();
                                billingClient.launchBillingFlow((Activity) mContext, flowParams);
                            } else {
                                //try to add item/product id "purchase" inside managed product in google play console
                                Toast.makeText(mContext, "Purchase Item not Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext,
                                    " Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
