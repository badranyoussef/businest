Feature: File Upload with Validation
  As a user,
  I want to be able to upload files when they meet the company's criteria,
  So that I can store them in the file management system.

  Scenario 1: Uploading a File
    Given I am logged in as a valid user,
    When I select the file I want to upload,
    Then I am required to enter the following information: name, description & topic.

  Scenario 2: File Rejection
    Given I am logged in as a valid user,
    When I select a file that does not match the company-approved formats,
    Then the file is rejected,
    And an error message is displayed informing me of the issue.

  Scenario 3: File Validation
    Given I have provided the required file information,
    When I choose to upload,
    Then the system validates the file information.
    And if the file validation fails:
    Then the file is not uploaded,
    And the process restarts,
    And an error message is displayed, explaining the issue.

  Scenario 4: Successful Upload
    Given I have provided the required file information,
    And the file has passed validation,
    When the upload is successful,
    Then I receive a message confirming the successful upload,
    And the company is notified of the upload.