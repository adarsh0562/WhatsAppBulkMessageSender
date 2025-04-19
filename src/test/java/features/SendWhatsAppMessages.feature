@WhatsAppTest
Feature: Send Bulk WhatsApp Messages

  Scenario: Send messages with images to multiple contacts
    Given User is logged into WhatsApp Web
    When User sends messages with images from Excel file
