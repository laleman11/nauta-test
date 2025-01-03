db = db.getSiblingDB("test");

db.tasks.insertMany([
    {
        title: "Task 1",
        description: "Description for task 1",
        state: "TO_DO",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 2",
        description: "Description for task 2",
        state: "IN_PROGRESS",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 3",
        description: "Description for task 3",
        state: "DONE",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 4",
        description: "Description for task 4",
        state: "TO_DO",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 5",
        description: "Description for task 5",
        state: "IN_PROGRESS",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 6",
        description: "Description for task 6",
        state: "DONE",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 7",
        description: "Description for task 7",
        state: "TO_DO",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 8",
        description: "Description for task 8",
        state: "IN_PROGRESS",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 9",
        description: "Description for task 9",
        state: "DONE",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        title: "Task 10",
        description: "Description for task 10",
        state: "TO_DO",
        createdAt: new Date(),
        updatedAt: new Date()
    }
]);
