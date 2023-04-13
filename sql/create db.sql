create or replace table categories
(
    ID          bigint auto_increment
        primary key,
    CODE        varchar(255) not null,
    DESCRIPTION text         null comment 'Chi tiết'
)
    comment 'lưu thông tin đề mục';

create or replace table config
(
    ID          bigint auto_increment
        primary key,
    CATEGORY_ID bigint       null,
    NAME        varchar(255) null,
    STATUS      int          null comment 'Trạng thái config(0/1 : inactive/active)',
    VALUE       varchar(255) null,
    ORDER_BY    int          null
);

create or replace table incompatible_dishes
(
    ID            bigint auto_increment
        primary key,
    INGREDIENT_ID bigint null comment 'Mã nguyên liệu nguồn',
    MAPPING_ID    bigint null comment 'Mã nguyên liệu kỵ với nguyên liệu nguồn',
    DESCRIPTION   int    null comment 'Chi tiết',
    STATUS        int    null comment 'Trạng thái (0/1 : inactive/active)'
)
    comment 'Danh sách các món kỵ nhau';

create or replace table ingredient
(
    ID              bigint auto_increment
        primary key,
    INGREDIENT_TYPE int          null comment 'Loại nguyên liệu(thịt, hạt, rau, món chín,...)',
    NAME            varchar(255) null comment 'Tên nguyên liệu',
    DESCRIPTION     varchar(255) null comment 'Chi tiết nguyên liệu',
    STATUS          int          null comment 'Trạng thái nguyên liệu(0/1 : inactive/active)'
)
    comment 'bảng nguyên liệu';

create or replace table menu
(
    ID          bigint auto_increment
        primary key,
    MEAL_TYPE   bigint   null comment 'Loại bữa ăn(sáng, trưa, chiều,...)',
    EFFECT_DATE date     null comment 'Ngày sử dụng',
    STATUS      int      null comment 'Trạng thái menu(1/0 : active/inactive)',
    CREATE_DATE datetime null,
    UPDATE_DATE datetime null
)
    comment 'Thực đơn được gợi ý';

create or replace table menu_ingredients
(
    MENU_ID        bigint null,
    INGREDIENTS_ID bigint null
)
    comment 'Bảng mapping giữa menu và nguyên liệu';

