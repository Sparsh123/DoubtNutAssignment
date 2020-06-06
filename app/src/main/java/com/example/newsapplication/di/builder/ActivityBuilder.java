package com.example.newsapplication.di.builder;



import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector(modules = OtpActivityModule.class)
    abstract OtpActivity bindOtpActivity();

    @ContributesAndroidInjector(modules = AddFleetActivityModule.class)
    abstract AddFleetActivity bindAddFleetActivity();

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = BuddyListingActivityModule.class)
    abstract BuddyListingActivity bindBuddyListingActivity();

    @ContributesAndroidInjector(modules = AddBuddyActivityModule.class)
    abstract AddBuddyActivity bindAddBuddyActivity();

    @ContributesAndroidInjector(modules = MyAccountActivityModule.class)
    abstract MyAccountActivity bindMyAccountActivity();

    @ContributesAndroidInjector(modules = MyProfileActivityModule.class)
    abstract MyProfileActivity bindMyProfileActivity();

    @ContributesAndroidInjector(modules = ChangeMobileActivityModule.class)
    abstract ChangeMobileActivity bindChangeMobileActivity();

    @ContributesAndroidInjector(modules = ChangePasswordActivityModule.class)
    abstract ChangePasswordActivity bindChangePasswordActivity();

    @ContributesAndroidInjector(modules = {
            TrackingBuddyActivityModule.class,
            TrackingMeFragmentProvider.class,
            IamTrackingFragmentProvider.class})
    abstract TrackingBuddyActivity bindTrackingBuddyActivity();

    @ContributesAndroidInjector(modules = {
            TaskActivityModule.class,
            AssignedtoMeFragmentProvider.class,
            IhaveAssignedFragmentProvider.class})
    abstract TaskActivity bindTaskActivity();

    @ContributesAndroidInjector(modules = BuddyProfileActivityModule.class)
    abstract BuddyProfileActivity bindBuddyDetailActivity();

//    @ContributesAndroidInjector(modules = TaskFilterActivityModule.class)
//    abstract TaskFilterActivity bindTaskFilterActivity();

    @ContributesAndroidInjector(modules = FleetListingActivityModule.class)
    abstract FleetListingActivity bindFleetListingActivity();

    @ContributesAndroidInjector(modules = NotificationActivityModule.class)
    abstract NotificationActivity bindNotificationActivity();

    @ContributesAndroidInjector(modules = BuddyFilterActivityModule.class)
    abstract BuddyFilterActivity bindBuddyFilterActivity();

    @ContributesAndroidInjector(modules = CreateTaskActivityModule.class)
    abstract CreateTaskActivity bindCreateTaskActivity();

    @ContributesAndroidInjector(modules = TaskDetailActivityModule.class)
    abstract TaskDetailActivity bindTaskDetailActivity();

    @ContributesAndroidInjector(modules = ReferAndEarnActivityModule.class)
    abstract ReferAndEarnActivity bindReferAndEarnActivity();

    @ContributesAndroidInjector(modules = TrackingBuddyDetailActivityModule.class)
    abstract TrackingBuddyDetailActivity bindTrackingBuddyDetailActivity();

    @ContributesAndroidInjector(modules = SettingActivityModule.class)
    abstract SettingsActivity bindSettingsActivity();

    @ContributesAndroidInjector(modules = EmergencyContactActivityModule.class)
    abstract EmergencyContactActivity bindEmergencyContactActivity();

    @ContributesAndroidInjector(modules = BuddyRequestActivityModule.class)
    abstract BuddyRequestActivity bindBuddyRequestActivity();

//    @ContributesAndroidInjector(modules = RejectTaskActivityModule.class)
//    abstract RejectTaskActivity bindRejectTaskActivity();

    @ContributesAndroidInjector(modules = AddEmergencyContactModule.class)
    abstract AddEmergencyContactActivity bindAddEmergencyContactActivity();

    @ContributesAndroidInjector(modules = EmergencyMessageActivityModule.class)
    abstract EmergencyMessageActivity bindEmergencyMessageActivity();

    @ContributesAndroidInjector(modules = {
            IntroActivityModule.class,
            IntroScreenFragmentProvider.class})
    abstract IntroActivity bindIntroActivity();

    @ContributesAndroidInjector(modules = ShareTripActivityModule.class)
    abstract ShareTripActivity bindShareTripActivity();

    @ContributesAndroidInjector(modules = WebViewActivityModule.class)
    abstract WebViewActivity bindWebViewActivity();

    @ContributesAndroidInjector(modules = {
            DynamicFormActivityModule.class,
            DynamicFragmentProvider.class})
    abstract DynamicFormActivity bindDynamicFormActivity();

    @ContributesAndroidInjector(modules = AppBlockActivityModule.class)
    abstract AppBlockActivity bindAppBlockActivity();

    @ContributesAndroidInjector(modules = ConsentActivityModule.class)
    abstract ConsentActivity bindConsentActivity();

    @ContributesAndroidInjector(modules = MessageActivityModule.class)
    abstract MessagesActivity bindMessageActivity();

    @ContributesAndroidInjector(modules = ChatActivityModule.class)
    abstract ChatActivity bindChatActivity();

    @ContributesAndroidInjector(modules = FormPreviewActivityModule.class)
    abstract FormPreviewActivity bindFormPreviewActivity();


    @ContributesAndroidInjector(modules = MyEarningsActivityModule.class)
    abstract MyEarningsActivity bindMyEarningsActivity();

    @ContributesAndroidInjector(modules = RideActivityModule.class)
    abstract RideActivity bindRideActivity();

    @ContributesAndroidInjector(modules = {
            AttendanceActivityModule.class,
            PunchInOutFragmentProvider.class,
            AttendanceFragmentProvider.class})
    abstract AttendanceActivity bindAttendanceActivity();

    @ContributesAndroidInjector(modules = {
            LeaveActivityModule.class,
            ApplyLeaveFragmentProvider.class,
            LeaveHistoryFragmentProvider.class,
            LeaveSummaryFragmentProvider.class,
            LeaveApprovalFragmentProvider.class})

    abstract LeaveActivity bindLeaveActivity();

}
