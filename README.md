# Pre-work - *Noded*

**Noted** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Adhish Ramkumar**

Time spent: **5** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Instructions

* Swipe up and down to scroll through the list of tasks
* Tap on task to edit it
* Tap and hold a task to delete it
* Tap the plus button on the top right to create a new task
* Cancel creating a new task by clicking the "x" on the top right of the new screen
* Save the new task by clicking the save icon at the top right of the new screen

## Notes

In the future, I'd like to add sorting functionality to the main activity as well as convert the activity to incorporate a navigation drawer. This will allow for tasks to be separated between different to-do lists which could be for different purposes. Additionally I would like to allow for to-do lists to be shared so that multiple users can create and delete items. This would be useful for a family where the entire family can request groceries while one member goes out shopping.

## Testing

This application was testing on a emulation of a Nexus 5X running API Level 23 (although support should go back until 16).

## License

    Copyright 2016 Adhish Ramkumar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.