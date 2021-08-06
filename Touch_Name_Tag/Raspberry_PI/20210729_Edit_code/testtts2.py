from google.cloud import texttospeech

client = texttospeech.TextToSpeechClient()
synthesis_input = texttospeech.types.SynthesisInput(text="안녕하세요. 저는 구글 TTS 입니다. 만나서 반가워요.")

voice = texttospeech.types.VoiceSelectionParams(
    language_code='ko-KR',
    ssml_gender=texttospeech.enums.SsmlVoiceGender.FEMALE)

audio_config = texttospeech.types.AudioConfig(
    audio_encoding=texttospeech.enums.AudioEncoding.MP3)

response = client.synthesize_speech(synthesis_input, voice, audio_config)

with open('googletts.mp3', 'wb') as out:
    out.write(response.audio_content)
    print('Audio content written to file "googletts.mp3"')



# api 인증을 통한 진행이 필요함.