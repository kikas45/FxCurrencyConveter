package com.example.simplecomposeproject.RateCoverter.ActivityLayouts

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.simplecomposeproject.MainActivity
import com.example.simplecomposeproject.OfflineRateHistoryActivity
import com.example.simplecomposeproject.R
import com.example.simplecomposeproject.RateCoverter.offlinehistory.RatesModel
import com.example.simplecomposeproject.RateCoverter.offlinehistory.adapter.RatesHistoryListAdapter

@Composable
fun OfflineRateHistoryScreen(
    items: List<RatesModel>,
    onBackClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    onItemClicked: (RatesModel) -> Unit,
    showDeleteAll: Boolean = false,
    errorMessageVisible: Boolean = false
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        // Create references for the main sections: action bar, drop shadow, recycler, error text.
        val (actionBar, dropShadow, recycler, errorText) = createRefs()

        // Action Bar (top bar)
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 2.dp)
                .constrainAs(actionBar) {
                    top.linkTo(parent.top)
                }
        ) {
            val (close, title, deleteAll) = createRefs()


            val context = LocalContext.current
            // on back press listener
            BackHandler {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                val activity = context as? Activity
                activity?.finish()
            }

            // Back Arrow Button (kept intact)
            IconButton(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    val activity = context as? Activity
                    activity?.finish() // End the activity when arrow is clicked
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .constrainAs(close) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_ios_new_24),
                    contentDescription = "Back"
                )
            }


            // Title Text ("Past Rates") with zero margin to the back arrow button.
            Text(
                text = "Past Rates",
                fontSize = 18.sp,
                color = Color.Black,
                maxLines = 1,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(close.end, margin = 0.dp)
                    end.linkTo(deleteAll.start, margin = 24.dp)
                    top.linkTo(close.top)
                    bottom.linkTo(close.bottom)
                    width = Dimension.fillToConstraints
                }
            )

            // "Delete All" Text (visible if needed)
            if (showDeleteAll) {
                Text(
                    text = "Delete All",
                    fontSize = 16.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .clickable { onDeleteAllClicked() }
                        .constrainAs(deleteAll) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                )
            }
        }

        // Drop Shadow View: using Surface with elevation to mimic a shadow
        Surface(
            tonalElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .constrainAs(dropShadow) {
                    top.linkTo(actionBar.bottom)
                }
        ) {}

        // Box for the adapter (Compose LazyColumn) positioned below the drop shadow.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(recycler) {
                    top.linkTo(dropShadow.bottom, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            RatesHistoryListAdapter(
                items = items,
                onItemClicked = onItemClicked
            )
        }

        // Error Message Text (centered; shown only if errorMessageVisible is true)
        if (errorMessageVisible) {
            Text(
                text = "Op's no Data for now",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 60.dp)
                    .constrainAs(errorText) {
                        centerTo(parent)
                    }
            )
        }
    }
}
