# Podcast Dataset

This document describes the sample data for Spotify Podcast Transcript dataset. The sample dataset includes 15 shows:
```
spotify:show:2Vh5jVI1EdMDeHs3N8XoME
spotify:show:2rPPvOAqUPSB6TqezjVjm5
spotify:show:4lbnMhRAmkGBxpGOb6yFAx
spotify:show:3yug4kwkuzzHOAdMe4ecpv
spotify:show:6r0bfT6lOLqDMh9ujnT1BW
spotify:show:2Mkrfx6HroyJiFjCnSRkOF
spotify:show:6ec9hDE62HkW7ckm2KC17L
spotify:show:1ZKHflAANeLU5C4XZ1Aa2O
spotify:show:5rlxB3eLnxgYzXFG8jKfel
spotify:show:4zwERdBdLsB57sSLU7dlFR
spotify:show:6CodmnLnIHc3MpzOOC92ml
spotify:show:22jqRD61y51WewOLvmHxfX
spotify:show:76bHN0zmR0ixLaledqBWrD
spotify:show:1mxdkoSv6URRpLtxMtAFp2
spotify:show:6aJTGYCYhFqIBsaNDPgiac
```

## `metadata.tsv`

This file contains the metadata of the shows (podcast) and their corresponding episodes. Each line is a record with the following attributes:
- `show_uri`:  Spotify uri for the show. e.g. spotify:show:7gozmLqbcbr6PScMjc0Zl4
- `show_name`:  Name of the show. e.g. Reply All
- `show_description`: Description of the show. e.g. "'A podcast about the internet' that is actual…”
- `publisher`: Publisher of the show. e.g. Gimlet
- `language`: Language the show is in in BCP 47 format. e.g. [en]
- `rss_link`: links of show rss feed. e.g. https://feeds.megaphone.fm/replyall
- `episode_uri`: Spotify uri for the episode. e.g. spotify:episode:4vYOibPeC270jJlnRoAVO6
- `episode_name`: Name of the episode. e.g. #109 Is Facebook Spying on You?
- `episode_description`: Description of the episode. e.g. “This year we’ve gotten one question more than …”
- `duration`: duration of the episode in minutes. e.g. 31.680000
- `show_filename_prefix`: Filename_prefix of the show. e.g. show_7gozmLqbcbr6PScMjc0Zl4
- `episode_filename_prefix`: Filename_prefix of the episode. e.g. 4vYOibPeC270jJlnRoAVO6

## Transcript data

A sample of 750 JSON files, i.e., the corresponding transcripts of the above 15 shows can be found [here](https://1drv.ms/u/s!AqMVofHWln_dh7Qp2cMc2Ynp7lMZ7A?e=DvMblH), password `dd2476`
