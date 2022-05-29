package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.plcoding.cleanarchitecturenoteapp.R
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note

@Composable
fun NoteItem(
	note: Note,
	modifier: Modifier = Modifier,
	cornerRadius: Dp = 10.dp,
	cutCornerSize: Dp = 30.dp,
	onDeleteClick: () -> Unit,
) {

	Box(
		modifier = modifier
	) {
		//// Main Card ////
		// match parent size means it's give the canvas the size after the parent measure is constraints
		Canvas(modifier = Modifier.matchParentSize()) {

			val clipPath = Path().apply {
				//// Top straight line ////
				lineTo(x = size.width - cutCornerSize.toPx(), y = 0f)
				//// Cut Line ////
				lineTo(x = size.width, cutCornerSize.toPx())
				//// Right straight line ////
				lineTo(x = size.width, size.height)
				//// Bottom straight line ////
				lineTo(x = 0f, size.height)
				//// Left straight line ////
				close()
			}

			clipPath(
				// Shape to clip drawing content within
				path = clipPath
			) {

				//// Rectangle ////
				drawRoundRect(
					color = Color(note.color),
					size = size,
					cornerRadius = CornerRadius(cornerRadius.toPx())
				)

				//// Corner folded ////
				drawRoundRect(
					color = Color(
						ColorUtils.blendARGB(
							note.color,
							0x000000,
							0.2f
						)
					),
					// Change the position of the second rect. -100f = move the corner up a little for better feeling
					topLeft = Offset(x = size.width - cutCornerSize.toPx(), y = -100f),
					size = Size(
						width = cutCornerSize.toPx() + 100f,
						height = cutCornerSize.toPx() + 100f
					),
					cornerRadius = CornerRadius(cornerRadius.toPx())
				)
			}

		}

		//// Rectangle content ////
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp)
				.padding(end = 32.dp)
		) {

			//// Title ////
			Text(
				text = note.title,
				style = MaterialTheme.typography.h6,
				color = MaterialTheme.colors.onSurface,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)

			Spacer(modifier = Modifier.height(8.dp))

			//// Content ////
			Text(
				text = note.content,
				style = MaterialTheme.typography.body1,
				color = MaterialTheme.colors.onSurface,
				maxLines = 10,
				overflow = TextOverflow.Ellipsis
			)

		}

		//// Delete Icon ////
		IconButton(
			onClick = onDeleteClick,
			modifier = Modifier
				.align(Alignment.BottomEnd)
		) {
			Icon(
				imageVector = Icons.Default.Delete,
				contentDescription = stringResource(R.string.delete_icon)
			)
		}
	}
}