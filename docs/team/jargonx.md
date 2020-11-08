---
layout: page
title: Neil Gupta's Project Portfolio Page
---

## Project: PIVOT (Police Investigation Virtual Organisational Tool)

PIVOT is a desktop application to assist the police investigators in keeping track of their investigations and relevant information. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 20 kLoC.

Given below are my contributions to the project.  

* **New Feature**: Implemented the `Witness` Class in PIVOT. (Pull Request [\#76](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/76))
  * What was done: Set up base class `Witness` and refactored the relevant elements of `Case` class to accommodate for the `Witness`.
  * What it does: The base class and refactored code will help further develop the `Case` class which is a major element of PIVOT.
  * Highlights: This enhancement affects existing commands and commands to be added in the future. It required an in-depth analysis of design alternatives. The implementation was carefully designed to integrate with the existing commands, keeping in mind of future extensions.

* **New Feature**: Added the ability to add and delete a `Witness` from a `Case`. (Pull Request [\#133](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/133))
  * What it does: Allows the user to add and delete a `Witness` into a specified `Case` one at a time.
  * Justification: This feature is highly pertinent as our application aims to help users better manage relevant information about investigations.
  * With the feature, users are able to keep track of the list of `Witness` of a `Case`, with all its relevant details.

* **New Feature**: Implemented `EditDescriptionCommand` in PIVOT. (Pull request [\#230](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/230))
  * What was done: Set up the command class as well as its `Parser` to parse the command in PIVOT.
  * Justification: This feature improves the product significantly because a user is clearer on which command to use for which situation.
  * What it does: The user can now edit a `Description` in a `Case` by calling `edit desc d:DESC`. It does not allow editing of a non-existent(i.e. blank) description.
  * Highlights: This enhancement builds on existing commands and allows extensions for commands added in the future. It follows existing design structures for its implementation. It was carefully designed to integrate with the existing commands, keeping in mind of future extensions.  

* **New Feature**: Implemented `DeleteDescriptionCommand` in PIVOT. (Pull request [\#230](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/230))
  * What was done: Set up the command class as well as its `Parser` to parse the command in PIVOT.
  * Justification: This feature improves the product significantly because a user is clearer on which command to use for which situation.
  * What it does: The user can now remove a `Description` from a `Case` by calling `delete desc`. It does not allow deleting of a non-existent(i.e. blank) description.
  * Highlights: This enhancement builds on existing commands. It follows existing design structures for its implementation. It was carefully designed to integrate with the existing commands.   


* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=jargonx&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Facilitated the discussions of certain implementations during group meetings. (`StateManager`, `UIStateManage`, Opening of Document etc)
  * Coordinated the deadlines of smaller iterations and the timings of the next meeting.

* **Enhancements to existing features**:
  * **Functional Features Updates**:
    * Updated the `DeleteCommand` to take in a delete type. (Pull Request [\#85](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/85))
    * Refactored `Case` class to remove the `Email` field.  (Pull Request [\#101](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/101))
    * Refactored `DeleteCommand` class to be abstract, added a `DeleteCaseCommand` class and refactored the relevant tests for `DeleteCaseCommand`. (Pull Request [\#114](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/114))
    * Updated the `AddDescriptionCommand` class to restrict its functionality. (Pull Request [\#230](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/230))
    
  * **Testing**: 
    * Wrote additional tests for existing features to increase coverage from 65% to 68% (Pull Request [\#160](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/160))

* **Documentation**:
  * User Guide:
    * Updated the command formats for all the features. (Pull Request [\#135](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/135))
    * Updated the Quick Start guide. (Pull Request [\#178](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/178))
    * Made minor cosmetic tweaks to all edit case persons commands for better understanding. (Pull Request [\#178](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/178))
  * Developer Guide:
    * Updated the `Logic` Segment of the Documentation. Included Sequence Diagrams for the Components for clarity. (Pull Request [\#154](https://github.com/AY2021S1-CS2103-F09-2/tp/pull/154))

* **Community**:
  * Reported bugs and suggestions for other teams in the class during [PE-Dry Run](https://github.com/jargonx/ped/issues)
