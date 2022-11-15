package com.hangoverstudios.men.photo.suite.editor.app.firebase;

public class RemoteConfigValues {
    private static final RemoteConfigValues ourRemote = new RemoteConfigValues();
    private String server;
    private String upgradeAppVersion;
    private String showInterstitialOnLaunch;
    private String showInterstitialOnExit;
    private String showInterstitialOnLaunchFBAd;
    private String showInterstitialAd;
    private String InterstitialOnlyGoogle;
    private String InterstitialOnlyFB;
    private String AdRotationPolicy;
    private String AdRotationPolicyNative;
    private String showNativeAd;
    private String showNativeAdOnDialog;
    private String showRewardVideoAd;
    private String showNativeAdOnMainScreen;
    private String NativeOnlyGoogle;
    private String NativeOnlyFB;
    private String showOurAppInterstitials;
    private String ourApps;
    private String imageUrl;
    private String OurAppsOnMainScreen;
    private String EnableWatermark;
    private String openAppAdFrequency;
    private  String updateFromPlaystore;
    private  String ShowRatingLayout;

    public static RemoteConfigValues getOurRemote() {
        return ourRemote;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUpgradeAppVersion() {
        return upgradeAppVersion;
    }

    public void setUpgradeAppVersion(String upgradeAppVersion) {
        this.upgradeAppVersion = upgradeAppVersion;
    }

    public String getShowInterstitialOnLaunch() {
        return showInterstitialOnLaunch;
    }

    public void setShowInterstitialOnLaunch(String showInterstitialOnLaunch) {
        this.showInterstitialOnLaunch = showInterstitialOnLaunch;
    }

    public String getShowInterstitialOnExit() {
        return showInterstitialOnExit;
    }

    public void setShowInterstitialOnExit(String showInterstitialOnExit) {
        this.showInterstitialOnExit = showInterstitialOnExit;
    }

    public String getShowNativeAdOnDialog() {
        return showNativeAdOnDialog;
    }

    public void setShowNativeAdOnDialog(String showNativeAdOnDialog) {
        this.showNativeAdOnDialog = showNativeAdOnDialog;
    }

    public String getShowRewardVideoAd() {
        return showRewardVideoAd;
    }

    public void setShowRewardVideoAd(String showRewardVideoAd) {
        this.showRewardVideoAd = showRewardVideoAd;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImaUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setShowInterstitialOnLaunchFBAd(String showInterstitialOnLaunchFBAd) {
        this.showInterstitialOnLaunchFBAd = showInterstitialOnLaunchFBAd;
    }

    public String getShowInterstitialAd() {
        return showInterstitialAd;
    }

    public void setShowInterstitialAd(String showInterstitialAd) {
        this.showInterstitialAd = showInterstitialAd;
    }

    public String getInterstitialOnlyGoogle() {
        return InterstitialOnlyGoogle;
    }

    public void setInterstitialOnlyGoogle(String interstitialOnlyGoogle) {
        InterstitialOnlyGoogle = interstitialOnlyGoogle;
    }

    public String getInterstitialOnlyFB() {
        return InterstitialOnlyFB;
    }

    public void setInterstitialOnlyFB(String interstitialOnlyFB) {
        InterstitialOnlyFB = interstitialOnlyFB;
    }

    public String getAdRotationPolicy() {
        return AdRotationPolicy;
    }

    public void setAdRotationPolicy(String adRotationPolicy) {
        AdRotationPolicy = adRotationPolicy;
    }

    public String getAdRotationPolicyNative() {
        return AdRotationPolicyNative;
    }

    public void setAdRotationPolicyNative(String adRotationPolicyNative) {
        AdRotationPolicyNative = adRotationPolicyNative;
    }

    public String getShowNativeAd() {
        return showNativeAd;
    }

    public void setShowNativeAd(String showNativeAd) {
        this.showNativeAd = showNativeAd;
    }

    public String getShowNativeAdOnMainScreen() {
        return showNativeAdOnMainScreen;
    }

    public void setShowNativeAdOnMainScreen(String showNativeAdOnMainScreen) {
        this.showNativeAdOnMainScreen = showNativeAdOnMainScreen;
    }

    public String getNativeOnlyGoogle() {
        return NativeOnlyGoogle;
    }

    public void setNativeOnlyGoogle(String nativeOnlyGoogle) {
        NativeOnlyGoogle = nativeOnlyGoogle;
    }

    public String getNativeOnlyFB() {
        return NativeOnlyFB;
    }

    public void setNativeOnlyFB(String nativeOnlyFB) {
        NativeOnlyFB = nativeOnlyFB;
    }

    public String getShowOurAppInterstitials() {
        return showOurAppInterstitials;
    }

    public void setShowOurAppInterstitials(String showOurAppInterstitials) {
        this.showOurAppInterstitials = showOurAppInterstitials;
    }

    public String getOurApps() {
        return ourApps;
    }

    public void setOurApps(String ourApps) {
        this.ourApps = ourApps;
    }

    public String getOurAppsOnMainScreen() {
        return OurAppsOnMainScreen;
    }

    public void setOurAppsOnMainScreen(String ourAppsOnMainScreen) {
        OurAppsOnMainScreen = ourAppsOnMainScreen;
    }

    public String getEnableWatermark() {
        return EnableWatermark;
    }

    public String getOpenAppAdFrequency() {
        return openAppAdFrequency;
    }

    public void setOpenAppAdFrequency(String openAppAdFrequency) {
        this.openAppAdFrequency = openAppAdFrequency;
    }

    public void setEnableWatermark(String enableWatermark) {
        EnableWatermark = enableWatermark;
    }

    private String  removeAds;
    private String enableIAPflag;

    public String getIAP_NOADS() {
        return IAP_NOADS;
    }

    public void setIAP_NOADS(String IAP_NOADS) {
        this.IAP_NOADS = IAP_NOADS;
    }

    private String IAP_NOADS ;

    public String getRemoveAds() {
        return removeAds;
    }

    public void setRemoveAds(String removeAds) {
        this.removeAds = removeAds;
    }

    public String getEnableIAPflag() {
        return enableIAPflag;
    }

    public void setEnableIAPflag(String enableIAPflag) {
        this.enableIAPflag = enableIAPflag;
    }

    public String getUpdateFromPlaystore() {
        return updateFromPlaystore;
    }

    public void setUpdateFromPlaystore(String updateFromPlaystore) {
        this.updateFromPlaystore = updateFromPlaystore;
    }

    public String getShowRatingLayout() {
        return ShowRatingLayout;
    }

    public void setShowRatingLayout(String showRatingLayout) {
        ShowRatingLayout = showRatingLayout;
    }
}
