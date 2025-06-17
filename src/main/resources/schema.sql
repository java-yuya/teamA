/*CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS albums ( /*口座(account)*/
    album_id INTEGER AUTO_INCREMENT PRIMARY KEY, /*口座ID*/
    artist VARCHAR(255) , /*holder*/
    title INTEGER NOT NULL, /*カテゴリ*/
    check(title in(0, 1)), /*カテゴリ選択*/
    release_date INTEGER not null default 0, /*金額*/
    user_id INTEGER not null,
    foreign key (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS musics ( /*bop*/
    music_id INTEGER AUTO_INCREMENT PRIMARY KEY,   /*bop id*/
    title INTEGER NOT NULL, /*収入支出選択*/
    check(title in(0, 1)),
    price INTEGER not null, /*amount*/
    duration DATE, /*日付*/
    method VARCHAR(255),
    album_id INTEGER NOT NULL, /*accountId*/
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (album_id) REFERENCES albums(album_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS favorites (
    user_id INTEGER NOT NULL,
    music_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, music_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (music_id) REFERENCES musics(music_id) ON DELETE CASCADE
);
*/

CREATE TABLE IF NOT EXISTS albums ( /**/
    album_id INTEGER AUTO_INCREMENT PRIMARY KEY, /*口座ID*/
    title VARCHAR(255) NOT NULL, /*口座名*/
    artist INTEGER NOT NULL,
    check(artist in(0, 1)),
    release_date INTEGER NOT NULL, /*金額*/
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    user_id INTEGER not null,
    foreign key (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS musics (
    music_id INTEGER AUTO_INCREMENT PRIMARY KEY, 
    title VARCHAR(255) NOT NULL,
    duration DATE, /*日付*/
    price INTEGER NOT NULL,
    method INTEGER NOT NULL,
    check(method in(0, 1)),
    album_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (album_id) REFERENCES albums(album_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS favorites (
    user_id INTEGER NOT NULL,
    music_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, music_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (music_id) REFERENCES musics(music_id) ON DELETE CASCADE
);
