db = db.getSiblingDB("test");
db.tasks.insertMany([
    { title: "task 1", desctiption: "Description", state: "IN_PROGRESS" },
    { title: "task 2", desctiption: "Description", state: "TO_DO" },
]);