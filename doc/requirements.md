Requirement specification
==========================

Purpose
--------

The use of sheets of paper with a checklist of items have long been as
an aid to ensure that each step of a process is completed. Checklists
are widely used in many industries where it is crucial that the
business processes are performed with utmost consistency. However, the
creation and printing of these checklists is a tedious process,
requiring expertise in multiple fields, which unfeasible in many small
businesses.

To provide small organizations with the ability to ensure the
consistent application of business processes by the use of checklists.
The goal is to reduce the number of error that are made while applying
these processes with checklists. The project aims to build a system
for the creation of template checklist and the filling out and
archiving of the filled out checklists.

Stakeholders
------------
We have identified a number of important stakeholders.

### Management

Management want to ensure that their business processes are applied
consistently. Management also wants accountability, answering the
question who signed-off on what.

### Process developers

The people defining the processes to be used in want to be able to
easily define the process in verifiable steps. The process may be
explicitly documented or simply tacit knowledge which is often the
case in small organizations.

### Employees on the floor

The employees on the floor want to perform process quickly and
accurately. They do not want to be slowed down by the filling out of
checklists unless they feel that this helps them perform their job
more quickly and with less mistakes.


Constraints
------------

### Solution Constraints

The following constraints are absolute requirements for the choice of
technologies.

#### HTML5

_Description_: The product shall use HTML5 for its userinterfcace.

_Rationale_: We do not have the resources to develop different
client's operating systems. HTML5 is the only system that supports
a large enough portion.

_Fit criterion_: The application is usable in a modern HTML5 browser
such as Microsoft Internet Explorer (versions 9 and above), Google Chrome,
Mozilla Firefox, Opera, and Apple Safari.

### Implementation environment

The application will be installed on servers using the Debian GNU/Linux
operating system. The system should be able to run on virtual machines
and on machines with vastly different capacities.

### Off-the-shelf software

### Scala
The product will use Scala and thus the Scala standard library and
language compiler are part of the software stack.

### Mongodb
Mongodb is to be the primary persistence back and therefore the MongoD
daemon and relevant client libraries are to be included.

### Nginx

The nginx web server is to be used as proxy for both performance and
security reasons.

### Anticipated Workplace environment

The users may not be tied to a traditional PC but may instead use a
smaller screen device such as a laptop, tablet or smart-phone.

Client applications may lose connectivity with the back-end, at any
time.

The application is to be used in small organizations with a people
focused culture.


Naming conventions and Definitions
----------------------------------

- Checklist: A list of items representing steps or conditions in a
  business process.
  
- Checklist Item: An item on a checklist which has a status and a description.

- Check (verb): The act of setting the status of an item on a
  checklist. Unless otherwise specified check refers to marking a
  Checklist-item as completed

- Checklist Template: The template of which a instance checklist is
  built upon.
  
- Sign-off: The act of verifying with a high degree of certainty that
  a something is completed. Sign-off also carries with it a
  responsibility, if a user signs off on something he or she is
  responsible for it.

Use Cases
=========

UC-1 Fill-out checklist
----------------------

A checklist user wants to create a new checklist for monitoring a
process. 

1. The user selects a checklist template. 
2. The system creates a new checklist. 
3. The user updates the status of an checklist item. 
4. The system records the new status.
5. Steps 3-4 are repeated until the checklist is complete.
6. Once complete the *user* marks the checklist as completed
7. The system marks the checklist as complete.

If the process is interrupted it may be resumed. The System is shown a
list of incomplete checklists. The user selects the checklist to be
resumed. The user then proceeds from steep 3.

UC-2 Sign-off checklist
------------------------
After completing a checklist a checklist user may be required to
Sign-off on it. 

1. The user selects a completed but not-yet signed-off checklist.
2. The system shows information about the checklist.
3. The user reviews the contents of the checklist and indicates that
   he wants to signs off.
4. The system records the signature and makes the checklist read-only.

If the user refuses the sign-off on a checklist he is taken to the
use-case ends without producing a sign-off.

UC-3 Manage organization users
-----------------------------

An organization administrator wants to manage the organization users.

Create
1. The administrator selects create
2. The administrator enters the properties for the user
3. The system stores the new user.

Retrieve
1. The administrator requests a listing of organization users
2. The administrator selects a user
3. The system displays information about the user.

Update
1. The administrator selects a user
2. The system displays the user information
3. The administrator updates the information
4. The system stores the updated information

Delete
1. The administrator selects a user
2. The system displays the user information
3. The administrator requests deletion
4. The system deletes the user.

UC-4 View filled out checklist
--------------------------------

Management or a process developer wants to view a filled out
checklist.

1. The user selects a checklist template
2. The system displays all filled-out checklists for that template.
3. The user selects a filled-out checklist.
4. The system displays a the contents of the checklist.


UC-5 View user responsible for sign-off
----------------------------------------

1. UC-4 is performed.
2. The user selects view sign-off
3. The system displays information about the sign-off and the
   responsible person.


UC-6 Manage checklist templates
-------------------------------

Process developers want to manage checklist templates.

Create
1. The user selects create
2. The user enters a name and description for the checklist.
3. The system creates the checklist template.
4. The user adds items to the checklist template.
5. The system records the items.
6. The user marks the checklist template as ready for use.
7. The system makes the template available to template users.

Retrieve
1. The user requests a listing of checklist templates
2. The user selects a checklist template.
3. The system displays information about the checklist template.


Update
1. The user selects a checklist template.
2. The system displays the checklist template.
3. The user updates the checklist template.
4. The system stores the updated information.

Delete
1. The user selects a checklist template.
2. The system displays the checklist template.
3. The user requests deletion of the checklist template.
4. The system deletes the checklist template.



Functional Requirements
========================

- F1: The system shall store checklist templates.
- F2: Stored checklist templates shall be modifiable without the loss
  of archived data.
- F3: The system should record filled out checklists.
- F4: The system should be able to list the checklists in an
  organization.
- F5: Changes to a checklist in the process of being filled out should
  be saved.
- F6: The system shall provide a be able to store sign-offs such that
  the responsible entity is recorded appropriately.
- F7: The system shall be able to revoke user access
- F8: The system shall to add users to an organization.
  
  
