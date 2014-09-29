
Feature: Incoming callcenter calls
   As a user I want the call center dashboard to function
   with multiple incoming calls, for obvious reasons

  Scenario Outline: One Broadsoft call to another Broadsoft agent shows up in the dashboard
    Given I am on the dashboard page
    And I have two GUnify SIP apps open with information "<sip_user1>", "<auth_user1>", "<sip_user2>", "<auth_user2>" and a call is started
    Then I will see the dashboard refresh
    And There will be 2 agents on a call

  Examples:
    |sip_user1  | auth_user1   | sip_user2  | auth_user2   |
    |2401003210 | gnolanUser1  | 2401003211 | gnolanUser2  |
    |2401003211 | gnolanUser2  | 2401003212 | gnolanUser3  |
    |2401003212 | gnolanUser3  | 2401011160 | gnolanUser4  |
    |2401011160 | gnolanUser4  | 2401011161 | gnolanUser5  |
    |2401011161 | gnolanUser5  | 2401011162 | gnolanUser6  |
    |2401011162 | gnolanUser6  | 2401011163 | gnolanUser7  |
    |2401011163 | gnolanUser7  | 2401011164 | gnolanUser8  |
    |2401011164 | gnolanUser8  | 2401011165 | gnolanUser9  |
    |2401011165 | gnolanUser9  | 2401011166 | gnolanUser10 |
    |2401011166 | gnolanUser10 | 2401011167 | gnolanUser11 |
    |2401011167 | gnolanUser11 | 2401011168 | gnolanUser12 |
    |2401011168 | gnolanUser12 | 2401011169 | gnolanUser13 |
    |2401011169 | gnolanUser13 | 2401003210 | gnolanUser1  |