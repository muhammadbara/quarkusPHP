USE [master]
GO
/****** Object:  Database [belajar]    Script Date: 4/26/2024 2:10:53 PM ******/
CREATE DATABASE [belajar]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'belajar', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\belajar.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'belajar_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\belajar_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [belajar] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [belajar].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [belajar] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [belajar] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [belajar] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [belajar] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [belajar] SET ARITHABORT OFF 
GO
ALTER DATABASE [belajar] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [belajar] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [belajar] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [belajar] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [belajar] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [belajar] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [belajar] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [belajar] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [belajar] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [belajar] SET  DISABLE_BROKER 
GO
ALTER DATABASE [belajar] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [belajar] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [belajar] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [belajar] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [belajar] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [belajar] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [belajar] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [belajar] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [belajar] SET  MULTI_USER 
GO
ALTER DATABASE [belajar] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [belajar] SET DB_CHAINING OFF 
GO
ALTER DATABASE [belajar] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [belajar] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [belajar] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [belajar] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [belajar] SET QUERY_STORE = ON
GO
ALTER DATABASE [belajar] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [belajar]
GO
/****** Object:  User [giry]    Script Date: 4/26/2024 2:10:53 PM ******/
CREATE USER [giry] FOR LOGIN [giry] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [bara]    Script Date: 4/26/2024 2:10:53 PM ******/
CREATE USER [bara] WITHOUT LOGIN WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [giry]
GO
USE [belajar]
GO
/****** Object:  Sequence [dbo].[MyEntity_SEQ]    Script Date: 4/26/2024 2:10:53 PM ******/
CREATE SEQUENCE [dbo].[MyEntity_SEQ] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 50
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
/****** Object:  Table [dbo].[category]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category](
	[catId] [bigint] IDENTITY(1,1) NOT NULL,
	[categoryName] [varchar](255) NULL,
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[catId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[department]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[department](
	[deptId] [bigint] IDENTITY(1,1) NOT NULL,
	[deptName] [varchar](255) NULL,
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[deptId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[event]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[event](
	[endDate] [date] NULL,
	[startDate] [date] NULL,
	[catId] [bigint] NULL,
	[etId] [bigint] NULL,
	[eventId] [bigint] IDENTITY(1,1) NOT NULL,
	[modifyDate] [datetime2](6) NULL,
	[audience] [varchar](255) NULL,
	[description] [varchar](255) NULL,
	[imagePath] [varchar](255) NULL,
	[needVolunteer] [varchar](255) NULL,
	[status] [varchar](255) NULL,
	[tittle] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[eventId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[eventImage]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[eventImage](
	[eventId] [bigint] NULL,
	[imageId] [bigint] IDENTITY(1,1) NOT NULL,
	[imageSize] [bigint] NULL,
	[imageName] [varchar](255) NULL,
	[imageType] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[imageId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[eventTheme]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[eventTheme](
	[etId] [bigint] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[etId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[history]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[history](
	[actionTime] [datetime2](6) NULL,
	[eventId] [bigint] NULL,
	[historyId] [bigint] IDENTITY(1,1) NOT NULL,
	[action] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[historyId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ojk]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ojk](
	[endDate] [date] NULL,
	[startDate] [date] NULL,
	[modifyDate] [datetime2](6) NULL,
	[ojkId] [bigint] IDENTITY(1,1) NOT NULL,
	[attachment] [varchar](255) NULL,
	[emailBody] [varchar](255) NULL,
	[priority] [varchar](255) NULL,
	[reminderType] [varchar](255) NULL,
	[status] [varchar](255) NULL,
	[title] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[ojkId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ojk_department_user]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ojk_department_user](
	[deptId] [bigint] NULL,
	[oduId] [bigint] IDENTITY(1,1) NOT NULL,
	[ojkId] [bigint] NULL,
	[userId] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[oduId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ojk_file]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ojk_file](
	[fileId] [bigint] IDENTITY(1,1) NOT NULL,
	[fileSize] [bigint] NULL,
	[ojkId] [bigint] NULL,
	[fileName] [varchar](255) NULL,
	[fileType] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[fileId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_event]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_event](
	[eventId] [bigint] NOT NULL,
	[userId] [bigint] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 4/26/2024 2:10:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[userId] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
	[position] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[userId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Index [UK_omm5csgp2pgjw9pik1js478oh]    Script Date: 4/26/2024 2:10:53 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_omm5csgp2pgjw9pik1js478oh] ON [dbo].[eventImage]
(
	[eventId] ASC
)
WHERE ([eventId] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UK_lckuaehw2peqbb0exq5ul6o0g]    Script Date: 4/26/2024 2:10:53 PM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_lckuaehw2peqbb0exq5ul6o0g] ON [dbo].[ojk_file]
(
	[ojkId] ASC
)
WHERE ([ojkId] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[event]  WITH CHECK ADD  CONSTRAINT [FKdlw8jr3s0yyeop094sgqyfuqn] FOREIGN KEY([etId])
REFERENCES [dbo].[eventTheme] ([etId])
GO
ALTER TABLE [dbo].[event] CHECK CONSTRAINT [FKdlw8jr3s0yyeop094sgqyfuqn]
GO
ALTER TABLE [dbo].[event]  WITH CHECK ADD  CONSTRAINT [FKt5pyl9t7on43ed6mromcmlmt2] FOREIGN KEY([catId])
REFERENCES [dbo].[category] ([catId])
GO
ALTER TABLE [dbo].[event] CHECK CONSTRAINT [FKt5pyl9t7on43ed6mromcmlmt2]
GO
ALTER TABLE [dbo].[eventImage]  WITH CHECK ADD  CONSTRAINT [FKb93hhpm01cfgw1xp17w3qnk06] FOREIGN KEY([eventId])
REFERENCES [dbo].[event] ([eventId])
GO
ALTER TABLE [dbo].[eventImage] CHECK CONSTRAINT [FKb93hhpm01cfgw1xp17w3qnk06]
GO
ALTER TABLE [dbo].[history]  WITH CHECK ADD  CONSTRAINT [FK5x9ttvpf8urap5bw1l0jpwado] FOREIGN KEY([eventId])
REFERENCES [dbo].[event] ([eventId])
GO
ALTER TABLE [dbo].[history] CHECK CONSTRAINT [FK5x9ttvpf8urap5bw1l0jpwado]
GO
ALTER TABLE [dbo].[ojk_department_user]  WITH CHECK ADD  CONSTRAINT [FK4o376fsgel9w91iafv930xcis] FOREIGN KEY([userId])
REFERENCES [dbo].[users] ([userId])
GO
ALTER TABLE [dbo].[ojk_department_user] CHECK CONSTRAINT [FK4o376fsgel9w91iafv930xcis]
GO
ALTER TABLE [dbo].[ojk_department_user]  WITH CHECK ADD  CONSTRAINT [FKldjbe58vgoc65309gb1hj8wgg] FOREIGN KEY([ojkId])
REFERENCES [dbo].[ojk] ([ojkId])
GO
ALTER TABLE [dbo].[ojk_department_user] CHECK CONSTRAINT [FKldjbe58vgoc65309gb1hj8wgg]
GO
ALTER TABLE [dbo].[ojk_department_user]  WITH CHECK ADD  CONSTRAINT [FKst9imqm0e3lowo08rvufgbbw5] FOREIGN KEY([deptId])
REFERENCES [dbo].[department] ([deptId])
GO
ALTER TABLE [dbo].[ojk_department_user] CHECK CONSTRAINT [FKst9imqm0e3lowo08rvufgbbw5]
GO
ALTER TABLE [dbo].[ojk_file]  WITH CHECK ADD  CONSTRAINT [FKcvhw6smwwlqyxpqbd9iverimp] FOREIGN KEY([ojkId])
REFERENCES [dbo].[ojk] ([ojkId])
GO
ALTER TABLE [dbo].[ojk_file] CHECK CONSTRAINT [FKcvhw6smwwlqyxpqbd9iverimp]
GO
ALTER TABLE [dbo].[user_event]  WITH CHECK ADD  CONSTRAINT [FKbkeyto9kpcwca67b53dyc5gkv] FOREIGN KEY([eventId])
REFERENCES [dbo].[event] ([eventId])
GO
ALTER TABLE [dbo].[user_event] CHECK CONSTRAINT [FKbkeyto9kpcwca67b53dyc5gkv]
GO
ALTER TABLE [dbo].[user_event]  WITH CHECK ADD  CONSTRAINT [FKotiu9rjqv7meppj8gbtvv596p] FOREIGN KEY([userId])
REFERENCES [dbo].[users] ([userId])
GO
ALTER TABLE [dbo].[user_event] CHECK CONSTRAINT [FKotiu9rjqv7meppj8gbtvv596p]
GO
ALTER TABLE [dbo].[event]  WITH CHECK ADD CHECK  (([audience]='YES' OR [audience]='NO'))
GO
ALTER TABLE [dbo].[event]  WITH CHECK ADD CHECK  (([needVolunteer]='YES' OR [needVolunteer]='NO'))
GO
ALTER TABLE [dbo].[event]  WITH CHECK ADD CHECK  (([status]='PUBLISH' OR [status]='FINISH' OR [status]='DRAFT' OR [status]='CANCELED'))
GO
ALTER TABLE [dbo].[history]  WITH CHECK ADD CHECK  (([action]='DELETE' OR [action]='CREATE' OR [action]='UPDATE'))
GO
ALTER TABLE [dbo].[ojk]  WITH CHECK ADD CHECK  (([priority]='HIGH' OR [priority]='MEDIUM' OR [priority]='LOW'))
GO
ALTER TABLE [dbo].[ojk]  WITH CHECK ADD CHECK  (([reminderType]='YEARLY' OR [reminderType]='MONTHLY' OR [reminderType]='WEEKLY' OR [reminderType]='DAILY'))
GO
ALTER TABLE [dbo].[ojk]  WITH CHECK ADD CHECK  (([status]='DISABLE' OR [status]='INACTIVE' OR [status]='ACTIVE'))
GO
USE [master]
GO
ALTER DATABASE [belajar] SET  READ_WRITE 
GO
