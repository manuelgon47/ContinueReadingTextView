# ContinueReadingTextView

<h3>Requirements:</h3>
In your attrs.xml:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    ...
    <declare-styleable name="ContinueReadingTextView">
        <attr name="continueReading" format="string" />
        <attr name="textCroppedLength" format="integer" />
    </declare-styleable>
    ...
</resources>
```

<h3>How to use:</h3>

In your layout xml, add the view com.your.package.ContinueReadingTextView in fact of a TextView:
```
<com.your.package.ContinueReadingTextView
    android:id="@+id/idContinueReadingTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="My custom text view"
    app:continueReading="Continue reading"
    app:textCroppedLength="350" />

```

<h6>Listener</h6>
Set the listener:
```
((ContinueReadingTextView) findViewById(R.id.idContinueReadingTextView).setContinueReadingListener(new ContinueReadingTextView.ContinueReadingListener() {
    @Override
    public void onClick(View textView) {
        // TODO
    }
});
```

<h6>Continue reading text</h6>
Change the 'continue reading' text:
```
app:continueReading="@string/continue_reading"
```
or:
```
app:continueReading="Continue reading"
```
or programatically:
```
setMaxLegth(350)
```

<h6>Length of cropped text</h6>
Using integer.xml:
```
app:textCroppedLength="@integer/croppedText"
```
or:
```
app:textCroppedLength="350"
```
or programatically:
```
setContinueReadingMessage(R.string.yourCustomString)
setContinueReadingMessage("Your continue reading")
```

