Feature:
  as a user
  I want to be able to upload files when they meet company’s own criteria
  So that I can get them into the file storing system

  Scenario: Upload file
  Given I am logged in as a valid user
  When I have chosen the file I want to upload
  Then I am required to enter relevant information, a. Name, b. Description, c. Topic, about the file.
